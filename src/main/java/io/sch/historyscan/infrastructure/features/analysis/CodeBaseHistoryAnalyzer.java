package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.*;
import io.sch.historyscan.domain.logging.spi.Logger;
import io.sch.historyscan.infrastructure.features.filesystem.FileSystemManager;
import io.sch.historyscan.infrastructure.hexagonalarchitecture.HexagonalArchitectureAdapter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    private CodeBaseHistory codeBaseHistory(File file) {
        try (var codeBase = Git.open(file)) {
            return new CodeBaseHistory(StreamSupport.stream(codeBase.log().all().call().spliterator(), false)
                    .map(revCommit -> initCodeBaseHistoryCommit(revCommit, codeBase))
                    .toList());

        } catch (IOException e) {
            logger.error("Unable to open codebase %s".formatted(file.getName()), e);
            throw new CodeBaseNotOpenedException("Unable to open codebase %s".formatted(file.getName()), e);
        } catch (NoHeadException e) {
            logger.error("Unable to find HEAD for codebase %s".formatted(file.getName()), e);
            throw new CodeBaseHeadNotFoundException("Unable to find HEAD for codebase %s".formatted(file.getName()), e);
        } catch (GitAPIException e) {
            logger.error("Unable to find log for codebase %s".formatted(file.getName()), e);
            throw new CodeBaseLogNotFoundException("Unable to find log for codebase %s".formatted(file.getName()), e);
        }
    }

    private CodeBaseFile initCodeBaseHistoryCommit(RevCommit revCommit, Git codeBase) {
        return new CodeBaseFile(
                new CodeBaseHistoryCommitInfo(
                        revCommit.getId().getName(),
                        revCommit.getAuthorIdent().getName(),
                        LocalDateTime.ofInstant(revCommit.getAuthorIdent().getWhenAsInstant(), ZoneId.systemDefault()),
                        revCommit.getShortMessage()
                ),
                commitFilesList(new GitCommit(revCommit), codeBase.getRepository(), codeBase)
        );
    }

    private List<CodeBaseHistoryCommitFile> commitFilesList(GitCommit commit, Repository repository, Git git) {
        try {
            var diffEntries = git.diff()
                    .setOldTree(prepareTreeParser(repository, commit.parentTree()))
                    .setNewTree(prepareTreeParser(repository, commit.getTree()))
                    .call();
            return diffEntries.stream()
                    .map(diffEntry -> initCodeBaseHistoryCommitFile(diffEntry, commit, repository))
                    .toList();
        } catch (GitAPIException e) {
            throw new CommitDiffException("Unable to find diff for commit %s".formatted(commit.getName()), e);
        }
    }

    private static CodeBaseHistoryCommitFile initCodeBaseHistoryCommitFile(DiffEntry diffEntry, GitCommit commit, Repository repository) {
        int linesAdded = 0;
        int linesDeleted = 0;

        try (var reader = repository.newObjectReader()) {

            CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
            CanonicalTreeParser newTreeIter = new CanonicalTreeParser();


            ObjectId oldTree = repository.resolve(commit.getName() + "^{tree}");
            ObjectId newTree = repository.resolve(commit.getName() + "^{tree}");

            oldTreeIter.reset(reader, oldTree);
            newTreeIter.reset(reader, newTree);


            switch (diffEntry.getChangeType()) {
                case ADD -> linesAdded += countLines(newTreeIter);
                case DELETE -> linesDeleted += countLines(oldTreeIter);
                case MODIFY -> {
                    linesAdded += countLines(newTreeIter);
                    linesDeleted += countLines(oldTreeIter);
                }
                default -> throw new CommitDiffException("Diff entry type not found %s".formatted(commit.getName()));
            }
            return new CodeBaseHistoryCommitFile(diffEntry.getNewPath(), linesAdded, linesDeleted);
        } catch (IOException e) {
            throw new CommitDiffException("Unable to read tree commit %s".formatted(commit.getName()), e);
        }
    }

    private static CanonicalTreeParser prepareTreeParser(Repository repository, RevTree tree) {
        try (var reader = repository.newObjectReader()) {
            var treeParser = new CanonicalTreeParser();
            if (tree != null) {
                treeParser.reset(reader, tree.getId());
            }
            return treeParser;
        } catch (IOException e) {
            throw new CommitDiffException("Unable to read tree %s".formatted(tree.getId()), e);
        }
    }

    private static int countLines(CanonicalTreeParser treeParser) {
        int count = 0;
        while (!treeParser.eof()) {
            treeParser.next();
            count++;
        }
        return count;
    }
}
