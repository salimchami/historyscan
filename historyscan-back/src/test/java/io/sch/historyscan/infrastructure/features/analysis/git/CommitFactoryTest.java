package io.sch.historyscan.infrastructure.features.analysis.git;

import io.sch.historyscan.common.GitTest;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.FileInfo;
import io.sch.historyscan.infrastructure.common.filesystem.FileSystemManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommitFactoryTest extends GitTest {

    private CommitFactory sut;
    private List<String> paths;
    private List<CodeBaseHistoryCommitFile> expectedCommitFiles;

    @BeforeEach
    void setUp() {
        FileSystemManager fileSystemManager = mock(FileSystemManager.class);
        when(fileSystemManager.allFilesFrom(codebase, ".git")).thenCallRealMethod();
        sut = new CommitFactory();
        paths = fileSystemManager.allFilesFrom(codebase, ".git");
        expectedCommitFiles = List.of(new CodeBaseHistoryCommitFile(
                new FileInfo("file1.txt", "file1.txt", true),
                3, 3, 0, 0));
    }

    @Test
    void createCommit() throws IOException {
        try (GitOperations gitOperations = new GitOperations(codebase)) {
            var commit = sut.createCommit(gitCommit, paths, gitOperations);
            assertThat(commit)
                    .isPresent()
                    .map(CodeBaseCommit::files)
                    .contains(expectedCommitFiles);
        }
    }
}