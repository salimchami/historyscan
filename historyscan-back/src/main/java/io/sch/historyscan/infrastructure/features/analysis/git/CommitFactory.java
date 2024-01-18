package io.sch.historyscan.infrastructure.features.analysis.git;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CommitFactory {
    public Optional<CodeBaseCommit> createCommit(RevCommit revCommit, List<String> codebaseCurrentFilesPaths, GitOperations gitOperations) {
        var codeBaseHistoryCommitFiles = commitFilesList(revCommit, gitOperations, codebaseCurrentFilesPaths);
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

    private List<CodeBaseHistoryCommitFile> commitFilesList(RevCommit gitCommit, GitOperations gitOperations, List<String> codebaseCurrentFilesPaths) {
        var files = new ArrayList<CodeBaseHistoryCommitFile>();
        var parentTree = parentTreeFrom(gitCommit);
        var commitTree = gitCommit.getTree();
        var diffs = gitOperations.commitDiffs(parentTree, commitTree);
        for (DiffEntry fileDiff : diffs) {
            if (fileDiff.getNewMode().getObjectType() == FileMode.REGULAR_FILE.getObjectType()
                && codebaseCurrentFilesPaths.stream().anyMatch(codebaseCurrentFile -> fileDiff.getNewPath().contains(codebaseCurrentFile))) {
                var diffParser = new DiffParser();
                var codeBaseHistoryCommitFile = diffParser.parseDiffs(gitOperations.getRepository(), fileDiff);
                files.add(codeBaseHistoryCommitFile);
            }
        }
        return files;
    }

    private RevTree parentTreeFrom(RevCommit gitCommit) {
        RevTree parentTree;
        if (gitCommit.getParentCount() > 0) {
            parentTree = gitCommit.getParent(0).getTree();
        } else {
            parentTree = null;
        }
        return parentTree;
    }
}
