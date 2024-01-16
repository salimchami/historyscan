package io.sch.historyscan.infrastructure.features.analysis.git;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo;
import io.sch.historyscan.domain.contexts.analysis.history.FileInfo;
import io.sch.historyscan.infrastructure.features.analysis.FileLinesCount;
import io.sch.historyscan.infrastructure.features.analysis.exceptions.CommitDiffException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public record CodebaseCurrentFiles(List<String> names, Git git, Repository repository) {

    public CodebaseCurrentFiles {
        names = List.copyOf(names);
    }

    public CodeBaseHistory toCodebaseHistoryFrom() throws IOException, GitAPIException {
        return new CodeBaseHistory(StreamSupport.stream(git.log().all().call().spliterator(), true)
                .map(revCommit -> initCodeBaseHistoryCommitFrom(revCommit, git, repository))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList());
    }

    public Optional<CodeBaseCommit> initCodeBaseHistoryCommitFrom(RevCommit revCommit, Git git, Repository repository) {
        var codeBaseHistoryCommitFiles = commitFilesList(revCommit, git, repository, names);
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
        var parentTree = new GitCommit(commit).parentTree();
        var commitTree = commit.getTree();
        var diffs = new GitRevTree(git, repository, parentTree, commitTree).diffs();
        for (DiffEntry fileDiff : diffs) {
            if (fileDiff.getNewMode().getObjectType() == FileMode.REGULAR_FILE.getObjectType()
                && codebaseCurrentFiles.stream().anyMatch(codebaseCurrentFile -> fileDiff.getNewPath().contains(codebaseCurrentFile))) {
                try (var out = new ByteArrayOutputStream();
                     DiffFormatter formatter = new DiffFormatter(out)) {
                    addCommitHistoryFile(repository, fileDiff, formatter, out, files);
                } catch (IOException e) {
                    throw new CommitDiffException("Unable to find diff for commit", e);
                }
            }
        }
        return files;
    }

    private static void addCommitHistoryFile(Repository repository, DiffEntry fileDiff, DiffFormatter formatter, ByteArrayOutputStream out, ArrayList<CodeBaseHistoryCommitFile> files) throws IOException {
        formatter.setRepository(repository);
        formatter.format(fileDiff);
        String diffText = out.toString();
        String[] diffLines = diffText.split("\r\n|\r|\n");
        var linesCount = FileLinesCount.from(diffLines, repository, fileDiff.getNewPath());
        boolean isFile = fileDiff.getNewMode().equals(FileMode.REGULAR_FILE);
        files.add(new CodeBaseHistoryCommitFile(
                new FileInfo(Paths.get(fileDiff.getNewPath()).getFileName().toString(),
                        fileDiff.getNewPath(), isFile), linesCount.nbLines(),
                linesCount.addedLines(), linesCount.deletedLines(), linesCount.modifiedLines()));
    }

}
