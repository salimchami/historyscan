package io.sch.historyscan.infrastructure.features.analysis.git

import io.sch.historyscan.common.GitTest
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile
import io.sch.historyscan.domain.contexts.analysis.history.FileInfo
import io.sch.historyscan.infrastructure.common.filesystem.FileSystemManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class CommitFactoryTest : GitTest() {
    private lateinit var sut: CommitFactory
    private lateinit var paths: List<String>
    private lateinit var expectedCommitFiles: List<CodeBaseHistoryCommitFile>

    @BeforeEach
    fun setUp() {
        val fileSystemManager = Mockito.mock(FileSystemManager::class.java)
        Mockito.`when`(fileSystemManager.allFilesFrom(codebase, ".git")).thenCallRealMethod()
        sut = CommitFactory()
        paths = fileSystemManager.allFilesFrom(codebase, ".git")
        expectedCommitFiles = listOf(
            CodeBaseHistoryCommitFile(
                FileInfo("file1.txt", "file1.txt", true),
                3, 3, 0, 1
            )
        )
    }

    @Test
    fun createCommit() {
        GitOperations(codebase).use { gitOperations ->
            val commit = sut.createCommit(gitCommit, paths, gitOperations)
            Assertions.assertThat(commit)
                .extracting("files")
                .isEqualTo(expectedCommitFiles)
        }
    }
}