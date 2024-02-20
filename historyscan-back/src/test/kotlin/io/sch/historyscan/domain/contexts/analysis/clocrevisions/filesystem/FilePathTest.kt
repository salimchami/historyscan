package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.util.stream.Stream

internal class FilePathTest {
    @ParameterizedTest
    @MethodSource("should_create_path_params")
    fun should_create_path(rootFolder: String?, codebaseName: String?, fullPath: String?, expectedPath: String?) {
        val filePath = FilePath(
            File(fullPath), rootFolder!!, codebaseName!!,
            "C:/Users/slim/historyscan/codebases"
        )
        val path = filePath.pathFromCodebaseName(filePath.currentFile)
        Assertions.assertThat(path).isEqualTo(expectedPath)
    }

    companion object {
        fun should_create_path_params(): Stream<Arguments?>? {
            val fullFilePath = "C:\\Users\\slim\\historyscan\\codebases\\historyscan\\historyscan-back" +
                    "\\src\\main\\java\\io\\sch\\historyscan\\domain\\contexts\\analysis" +
                    "\\clocrevisions\\filesystem\\CodeBaseFile.java"
            val expectedFilePath =
                "historyscan/historyscan-back/src/main/java/io/sch/historyscan/domain/contexts/analysis/clocrevisions/filesystem/CodeBaseFile.java"
            val fullFolderPath = "C:\\Users\\slim\\historyscan\\codebases\\historyscan\\historyscan-back" +
                    "\\src\\main\\java\\io\\sch\\historyscan\\domain\\contexts\\analysis" +
                    "\\clocrevisions\\filesystem"
            val expectedFolderPath =
                "historyscan/historyscan-back/src/main/java/io/sch/historyscan/domain/contexts/analysis/clocrevisions/filesystem"
            val codebaseName = "historyscan"
            return Stream.of(
                Arguments.of("/", codebaseName, fullFilePath, expectedFilePath),
                Arguments.of(codebaseName, codebaseName, fullFilePath, expectedFilePath),
                Arguments.of("historyscan/domain", codebaseName, fullFilePath, expectedFilePath),
                Arguments.of("domain", codebaseName, fullFilePath, expectedFilePath),
                Arguments.of("domain", codebaseName, fullFolderPath, expectedFolderPath)
            )
        }
    }
}