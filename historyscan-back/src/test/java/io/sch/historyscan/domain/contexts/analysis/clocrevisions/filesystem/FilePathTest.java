package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FilePathTest {

    public static Stream<Arguments> should_create_path_params() {
        var fullFilePath = "C:\\Users\\slim\\historyscan\\codebases\\historyscan\\historyscan-back" +
                           "\\src\\main\\java\\io\\sch\\historyscan\\domain\\contexts\\analysis" +
                           "\\clocrevisions\\filesystem\\CodeBaseFile.java";
        var expectedFilePath = "historyscan-back/src/main/java/io/sch/historyscan/domain/contexts/analysis/clocrevisions/filesystem/CodeBaseFile.java";
        var fullFolderPath = "C:\\Users\\slim\\historyscan\\codebases\\historyscan\\historyscan-back" +
                           "\\src\\main\\java\\io\\sch\\historyscan\\domain\\contexts\\analysis" +
                           "\\clocrevisions\\filesystem";
        var expectedFolderPath = "historyscan-back/src/main/java/io/sch/historyscan/domain/contexts/analysis/clocrevisions/filesystem";
        final String codebaseName = "historyscan";
        return Stream.of(
                Arguments.of("/", codebaseName, fullFilePath, expectedFilePath),
                Arguments.of(codebaseName, codebaseName, fullFilePath, expectedFilePath),
                Arguments.of("historyscan/domain", codebaseName, fullFilePath, expectedFilePath),
                Arguments.of("domain", codebaseName, fullFilePath, expectedFilePath),
                Arguments.of("domain", codebaseName, fullFolderPath, expectedFolderPath)
        );
    }

    @ParameterizedTest
    @MethodSource("should_create_path_params")
    void should_create_path(String rootFolder, String codebaseName, String fullPath, String expectedPath) {
        var filePath = new FilePath(new File(fullPath), rootFolder, codebaseName,
                "C:/Users/slim/historyscan/codebases");
        var path = filePath.pathFromCodebaseName(filePath.currentFile());
        assertThat(path).isEqualTo(expectedPath);
    }
}