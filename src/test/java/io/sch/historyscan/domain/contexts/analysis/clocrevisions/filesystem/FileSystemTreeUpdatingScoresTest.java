package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.stream.Stream;

import static io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemNodeSerializerUtils.serializeExpectedRoot;
import static io.sch.historyscan.fake.CodeBaseCommitFake.defaultHistory;
import static org.assertj.core.api.Assertions.assertThat;

class FileSystemTreeUpdatingScoresTest {
    public static Stream<Arguments> should_load_file_system_tree_from_disk_params() {
        return Stream.of(
                Arguments.of("domain", "domain-with-scores"),
                Arguments.of("/", "theglobalproject-with-scores"),
                Arguments.of("", "theglobalproject-with-scores")
        );
    }

    @ParameterizedTest
    @MethodSource("should_load_file_system_tree_from_disk_params")
    void should_load_file_system_tree_from_disk(String rootFolderName, String expectedRootTestCase) throws IOException {
        final RootFolder rootFolder = RootFolder.of(rootFolderName, "theglobalproject");
        var fsTree = new FileSystemTree(rootFolder);
        var codebasesResource = new ClassPathResource("codebases/theglobalproject");

        fsTree.addFileNodes(codebasesResource.getFile(), "theglobalproject");
        fsTree = fsTree
                .updateFilesScoreFrom(defaultHistory().commits())
                .then()
                .updateFoldersScore();

        final var expectedRoot = serializeExpectedRoot(expectedRootTestCase);
        assertThat(fsTree)
                .extracting(FileSystemTree::getRoot)
                .isEqualTo(expectedRoot);
    }

}