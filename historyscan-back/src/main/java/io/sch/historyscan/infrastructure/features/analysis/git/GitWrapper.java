package io.sch.historyscan.infrastructure.features.analysis.git;

import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GitWrapper {
    private final CommitFactory commitFactory;
    private final File codebase;
    private final List<String> codebaseCurrentFilesPaths;

    public GitWrapper(File codebase, List<String> codebaseCurrentFilesPaths) {
        this.codebase = codebase;
        this.codebaseCurrentFilesPaths = codebaseCurrentFilesPaths;
        this.commitFactory = new CommitFactory();
    }

    public CodeBaseHistory history() throws GitAPIException, IOException {
        try (GitOperations gitOperations = new GitOperations(codebase)) {
            return new CodeBaseHistory(gitOperations.getCommits().stream()
                    .map(revCommit -> commitFactory.createCommit(revCommit, codebaseCurrentFilesPaths, gitOperations))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList());
        }
    }
}
