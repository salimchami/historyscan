package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.RootFolder.Companion.of
import io.sch.historyscan.fake.CodeBaseCommitFake
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.core.io.ClassPathResource
import java.io.IOException
import java.util.stream.Stream

internal class FileSystemTreeUpdatingScoresTest {
    @ParameterizedTest
    @MethodSource("should_load_file_system_tree_from_disk_params")
    @Throws(IOException::class)
    fun should_load_file_system_tree_from_disk(rootFolderName: String, expectedRootTestCase: String) {
        val codebaseName = "theglobalproject"
        val rootFolder = of(rootFolderName, codebaseName)
        var fsTree = FileSystemTree(
            rootFolder
        )
        val codebaseResource = ClassPathResource("codebases/theglobalproject")
        val codebasesResource = ClassPathResource("codebases")

        fsTree.createFrom(
            CodeBaseFile(
                codebaseResource.file, rootFolder,
                codebasesResource.file.path
            )
        )
        fsTree = fsTree
            .updateFilesScoreFrom(CodeBaseCommitFake.defaultHistory()!!.commits)
            .then()
            .updateFoldersScore()

        val expectedRootFolder = FileSystemNodeSerializerUtils.serializeExpectedRoot(expectedRootTestCase)
        Assertions.assertThat(fsTree)
            .extracting("rootFolder")
            .isEqualTo(expectedRootFolder)
    }

    companion object {
        @JvmStatic
        fun should_load_file_system_tree_from_disk_params(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("domain", "domain-with-scores"),
                Arguments.of("/", "theglobalproject-with-scores"),
                Arguments.of("", "theglobalproject-with-scores")
            )
        }
    }
}