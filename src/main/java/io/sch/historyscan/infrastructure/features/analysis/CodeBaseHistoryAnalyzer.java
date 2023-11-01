package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.*;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo;
import io.sch.historyscan.domain.contexts.analysis.history.HistoryAnalyzer;
import io.sch.historyscan.domain.logging.spi.Logger;
import io.sch.historyscan.infrastructure.common.filesystem.FileSystemManager;
import io.sch.historyscan.infrastructure.hexagonalarchitecture.HexagonalArchitectureAdapter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
@HexagonalArchitectureAdapter
public class CodeBaseHistoryAnalyzer implements HistoryAnalyzer {

    private final String codebasesFolder;
    private final FileSystemManager fileSystemManager;
    private final Logger logger;

    public CodeBaseHistoryAnalyzer(@Value("${io.sch.historyscan.codebases.folder}") String codebasesFolder,
                                   FileSystemManager fileSystemManager, Logger logger) {
        this.codebasesFolder = codebasesFolder;
        this.fileSystemManager = fileSystemManager;
        this.logger = logger;
    }

    @Override
    public Optional<CodeBaseHistory> of(String codeBaseName) {
        return this.fileSystemManager.findFolder(codebasesFolder, codeBaseName)
                .map(this::codeBaseHistory);
    }

    private CodeBaseHistory codeBaseHistory(File codebase) {
        try (var repository = new FileRepositoryBuilder().setGitDir(Paths.get(codebase.toString(), ".git").toFile()).build();
             var git = new Git(repository)) {
            git.fetch().call();
            git.pull().call();
            var codebaseCurrentFiles = fileSystemManager.allFilesFrom(codebase, ".git");
            return new CodeBaseHistory(StreamSupport.stream(git.log().all().call().spliterator(), false)
                    .map(revCommit -> initCodeBaseHistoryCommit(revCommit, git, repository, codebaseCurrentFiles))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList());
        } catch (IOException e) {
            logger.error("Unable to open codebase %s".formatted(codebase.getName()), e);
            throw new CodeBaseNotOpenedException("Unable to open codebase %s".formatted(codebase.getName()), e);
        } catch (NoHeadException e) {
            logger.error("Unable to find HEAD for codebase %s".formatted(codebase.getName()), e);
            throw new CodeBaseHeadNotFoundException("Unable to find HEAD for codebase %s".formatted(codebase.getName()), e);
        } catch (GitAPIException e) {
            logger.error("Unable to find log for codebase %s".formatted(codebase.getName()), e);
            throw new CodeBaseLogNotFoundException("Unable to find log for codebase %s".formatted(codebase.getName()), e);
        }
    }

    private Optional<CodeBaseCommit> initCodeBaseHistoryCommit(RevCommit revCommit, Git git, Repository repository, List<String> codebaseCurrentFiles) {
        final List<CodeBaseHistoryCommitFile> codeBaseHistoryCommitFiles = commitFilesList(revCommit, git, repository, codebaseCurrentFiles);
        if (codeBaseHistoryCommitFiles.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new CodeBaseCommit(
                new CodeBaseHistoryCommitInfo(
                        revCommit.getId().getName(),
                        revCommit.getAuthorIdent().getName(),
                        LocalDateTime.ofInstant(revCommit.getAuthorIdent().getWhenAsInstant(), ZoneId.systemDefault()),
                        revCommit.getShortMessage()
                ),
                codeBaseHistoryCommitFiles.stream().filter(file -> file.cloc() > 0).toList()
        ));
    }

    private List<CodeBaseHistoryCommitFile> commitFilesList(RevCommit commit, Git git, Repository repository,
                                                            List<String> codebaseCurrentFiles) {
        var files = new ArrayList<CodeBaseHistoryCommitFile>();
        var parentTree = parentTree(commit);
        var commitTree = commit.getTree();
        var diffs = commitDiffs(git, repository, parentTree, commitTree);
        for (DiffEntry fileDiff : diffs) {
            if (fileDiff.getNewMode().getObjectType() == FileMode.REGULAR_FILE.getObjectType()
                    && codebaseCurrentFiles.stream().anyMatch(codebaseCurrentFile -> fileDiff.getNewPath().contains(codebaseCurrentFile))) {
                try (var out = new ByteArrayOutputStream();
                     DiffFormatter formatter = new DiffFormatter(out)) {
                    formatter.setRepository(repository);
                    formatter.format(fileDiff);
                    String diffText = out.toString();
                    String[] diffLines = diffText.split("\r\n|\r|\n");
                    var linesCount = FileLinesCount.from(diffLines, repository, fileDiff.getNewPath());
                    files.add(new CodeBaseHistoryCommitFile(fileDiff.getNewPath(), linesCount.nbLines(),
                            linesCount.addedLines(), linesCount.deletedLines(), linesCount.modifiedLines()));
                } catch (IOException e) {
                    throw new CommitDiffException("Unable to find diff for commit", e);
                }
            }
        }
        return files;
    }

    private List<DiffEntry> commitDiffs(Git git, Repository repository, RevTree parentTree, RevTree commitTree) {
        try {
            if (parentTree != null) {
                return git.diff()
                        .setOldTree(prepareTreeParser(repository, parentTree))
                        .setNewTree(prepareTreeParser(repository, commitTree))
                        .call();
            } else {
                return git.diff()
                        .setOldTree(prepareTreeParser(repository, null))
                        .setNewTree(prepareTreeParser(repository, commitTree))
                        .call();
            }
        } catch (GitAPIException e) {
            throw new CommitDiffException("Unable to find diff for commit", e);
        }
    }

    private CanonicalTreeParser prepareTreeParser(Repository repository, RevTree tree) {
        try (ObjectReader reader = repository.newObjectReader()) {
            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            if (tree != null) {
                treeParser.reset(reader, tree.getId());
            }
            return treeParser;
        } catch (IOException e) {
            throw new CommitDiffException("Unable to find diff for commit", e);
        }
    }

    private static RevTree parentTree(RevCommit commit) {
        RevTree parentTree;
        if (commit.getParentCount() > 0) {
            parentTree = commit.getParent(0).getTree();
        } else {
            parentTree = null; // No parent for the initial commit
        }
        return parentTree;
    }
}
