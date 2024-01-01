package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FileInfoTest {

    public static Stream<Arguments> should_create_file_path_from_a_path_part_params() {
        var commonFileInfo = new FileInfo("file.ts", "path/to/file.ts", true);
        return Stream.of(
                Arguments.of(commonFileInfo, "path", "path"),
                Arguments.of(commonFileInfo, "to", "path/to"),
                Arguments.of(commonFileInfo, "file.ts", "path/to/file.ts"),
                Arguments.of(new FileInfo("junit", "junit5/junit-jupiter-api/src/main/java/org/junit", false), "junit", "junit5/junit-jupiter-api/src/main/java/org/junit"),
                Arguments.of(new FileInfo("documentation.gradle.kts", "junit5/documentation/documentation.gradle.kts", true), "documentation", "junit5/documentation")
        );
    }

    @ParameterizedTest
    @MethodSource("should_create_file_path_from_a_path_part_params")
    void should_create_file_path_from_a_path_part(FileInfo fileInfo, String part, String expectedPath) {
        var path = fileInfo.pathFrom(part);
        assertThat(path).isEqualTo(expectedPath);
    }

    public static Stream<Arguments> should_find_is_file_path_from_a_path_part_params() {
        var fileTs = new FileInfo("file.ts", "path/to/file.ts", true);
        var domain = new FileInfo("domain",
                "theglobalproject/src/main/java/io/mycompany/theproject/domain", false);
        return Stream.of(
                Arguments.of(domain, "domain", false),
                Arguments.of(fileTs, "to", false),
                Arguments.of(fileTs, "file.ts", true)
        );
    }

    @ParameterizedTest
    @MethodSource("should_find_is_file_path_from_a_path_part_params")
    void should_find_is_file_path_from_a_path_part(FileInfo fileInfo, String pathPart, boolean expectedIsFile) {
        var isFile = fileInfo.isFileFrom(pathPart);
        assertThat(isFile).isEqualTo(expectedIsFile);
    }

    public static Stream<Arguments> should_find_parent_path_from_a_part_of_path_params() {
        return Stream.of(
                Arguments.of(new FileInfo("file.ts", "path/to/file.ts", true), "to", "path"),
                Arguments.of(new FileInfo(".file.ts", "path/.file.ts", true), ".file.ts", "path"),
                Arguments.of(new FileInfo("documentation.gradle.kts", "junit5/documentation/documentation.gradle.kts", true), "documentation", "junit5")
        );
    }

    @ParameterizedTest
    @MethodSource("should_find_parent_path_from_a_part_of_path_params")
    void should_find_parent_path_from_a_part_of_path(FileInfo fileInfo, String part, String expectedParentPath) {
        var parentPath = fileInfo.parentPathFrom(part);
        assertThat(parentPath).isEqualTo(expectedParentPath);
    }
}
