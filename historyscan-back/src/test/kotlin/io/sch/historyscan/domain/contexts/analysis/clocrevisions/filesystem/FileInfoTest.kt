package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class FileInfoTest {
    @ParameterizedTest
    @MethodSource("should_create_file_path_from_a_path_part_params")
    fun should_create_file_path_from_a_path_part(fileInfo: FileInfo, part: String, expectedPath: String) {
        val path = fileInfo.pathFrom(part)
        Assertions.assertThat(path).isEqualTo(expectedPath)
    }

    @ParameterizedTest
    @MethodSource("should_find_path_parts_even_if_rootfolder_contains_slashes_params")
    fun should_find_path_parts_even_if_rootfolder_contains_slashes(
        rootFolder: String,
        fileName: String,
        filePath: String,
        expectedParts: List<String>
    ) {
        val fileInfo = FileInfo(
            fileName, filePath, true, 40
        )
        val pathParts: List<String> = fileInfo.pathParts(rootFolder)
        Assertions.assertThat(pathParts).isEqualTo(expectedParts)
    }

    @ParameterizedTest
    @MethodSource("should_find_is_file_path_from_a_path_part_params")
    fun should_find_is_file_path_from_a_path_part(fileInfo: FileInfo, pathPart: String, expectedIsFile: Boolean) {
        val isFile = fileInfo.isFileFrom(pathPart, "domain")
        Assertions.assertThat(isFile).isEqualTo(expectedIsFile)
    }

    @ParameterizedTest
    @MethodSource("should_find_parent_path_from_a_part_of_path_params")
    fun should_find_parent_path_from_a_part_of_path(fileInfo: FileInfo, part: String, expectedParentPath: String) {
        val parentPath = fileInfo.parentPathFrom(part)
        Assertions.assertThat(parentPath).isEqualTo(expectedParentPath)
    }

    companion object {
        @JvmStatic
        fun should_create_file_path_from_a_path_part_params(): Stream<Arguments> {
            val commonFileInfo = FileInfo("file.ts", "path/to/file.ts", true, 40)
            return Stream.of(
                Arguments.of(commonFileInfo, "path", "path"),
                Arguments.of(commonFileInfo, "to", "path/to"),
                Arguments.of(commonFileInfo, "file.ts", "path/to/file.ts"),
                Arguments.of(
                    FileInfo(
                        "junit", "junit5/junit-jupiter-api/src/main/java/org/junit",
                        false, 40
                    ), "junit", "junit5/junit-jupiter-api/src/main/java/org/junit"
                ),
                Arguments.of(
                    FileInfo(
                        "documentation.gradle.kts", "junit5/documentation/documentation.gradle.kts",
                        true, 40
                    ), "documentation", "junit5/documentation"
                ),
                Arguments.of(
                    FileInfo(
                        "mycompany/theproject/domain", "theglobalproject/src/main/java/io/mycompany/theproject/domain",
                        true, 40
                    ), "mycompany/theproject/domain", "theglobalproject/src/main/java/io/mycompany/theproject/domain"
                )

            )
        }

        @JvmStatic
        fun should_find_path_parts_even_if_rootfolder_contains_slashes_params(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("path", "file.ts", "path/to/file.ts", listOf("path", "to", "file.ts")),
                Arguments.of("path/to", "file.ts", "path/to/file.ts", listOf("path/to", "file.ts")),
                Arguments.of(
                    "folder",
                    "folder-application",
                    "folder/folder-application",
                    listOf("folder", "folder-application")
                ),
                Arguments.of(
                    "domain",
                    "boundedcontexts",
                    "theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts",
                    listOf("theglobalproject/src/main/java/io/mycompany/theproject/domain", "boundedcontexts")
                ),
                Arguments.of(
                    "java/io/mycompany/theproject/domain",
                    "boundedcontexts",
                    "theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts",
                    listOf("theglobalproject/src/main/java/io/mycompany/theproject/domain", "boundedcontexts")
                )
            )
        }

        @JvmStatic
        fun should_find_is_file_path_from_a_path_part_params(): Stream<Arguments> {
            val fileTs = FileInfo("file.ts", "path/to/file.ts", true, 40)
            val domain = FileInfo(
                "domain",
                "theglobalproject/src/main/java/io/mycompany/theproject/domain", false, 0
            )
            return Stream.of(
                Arguments.of(domain, "domain", false),
                Arguments.of(fileTs, "to", false),
                Arguments.of(fileTs, "file.ts", true)
            )
        }

        @JvmStatic
        fun should_find_parent_path_from_a_part_of_path_params(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(FileInfo("file.ts", "path/to/file.ts", true, 40), "to", "path"),
                Arguments.of(FileInfo(".file.ts", "path/.file.ts", true, 40), ".file.ts", "path"),
                Arguments.of(
                    FileInfo(
                        "documentation.gradle.kts",
                        "junit5/documentation/documentation.gradle.kts",
                        true,
                        40
                    ), "documentation", "junit5"
                )
            )
        }
    }
}
