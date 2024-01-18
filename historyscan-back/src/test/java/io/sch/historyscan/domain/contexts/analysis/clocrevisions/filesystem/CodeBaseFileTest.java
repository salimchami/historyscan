package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CodeBaseFileTest {
    private File codebaseFile;
    private File codebasesFile;

    public static Stream<Arguments> should_find_children_from_root_folder_and_without_ignored_files_params() {
        return Stream.of(
                Arguments.of("/", List.of(
                        "theglobalproject",
                        "src",
                        "main",
                        "java",
                        "io",
                        "mycompany",
                        "theproject",
                        "domain",
                        "boundedcontexts",
                        "featureA",
                        "refactored",
                        "Extensions.java",
                        "Loading.java",
                        "Extensions.java",
                        "Loading.java",
                        "featureB",
                        "FinallyLoading.java",
                        "infrastructure",
                        "boundedcontexts",
                        "featureA",
                        "ExtensionsAdapter.java",
                        "build.gradle"

                ))
//                Arguments.of("domain", List.of(
//                        "domain",
//                        "boundedcontexts",
//                        "featureA",
//                        "refactored",
//                        "Extensions.java",
//                        "Loading.java",
//                        "Extensions.java",
//                        "Loading.java",
//                        "featureB",
//                        "FinallyLoading.java"))
        );
    }

    @BeforeEach
    void setUp() throws IOException {
        var codebaseResource = new ClassPathResource("codebases/theglobalproject");
        var codebasesResource = new ClassPathResource("codebases");
        codebaseFile = codebaseResource.getFile();
        codebasesFile = codebasesResource.getFile();
    }

    @ParameterizedTest
    @MethodSource("should_find_children_from_root_folder_and_without_ignored_files_params")
    void should_find_children_from_root_folder_and_without_ignored_files(String rootFolderValue, List<String> expectedFiles) {
        final String codeBaseName = "theglobalproject";
        RootFolder rootFolder = RootFolder.of(rootFolderValue, codeBaseName);
        var sut = new CodeBaseFile(codebaseFile, rootFolder, codebasesFile.getPath());
        var children = sut.children();
        assertThat(children)
                .extracting(FileInfo::name)
                .containsExactlyInAnyOrderElementsOf(expectedFiles);
    }
}