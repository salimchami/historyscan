package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ClusteredClocRevisionsTest {

    private ClusteredClocRevisions sut;

    @BeforeEach
    void setUp() {
        var root = "src/main/java/io.mystartup.project";
        var rootBoundedContexts = "%s/boundedcontexts".formatted(root);
        var revisions1 = new ClocRevisionsFile(new FileInfo("TheApplication.java", "%s/TheApplication.java".formatted(root)), new RevisionScore(1));
        var revisions2 = new ClocRevisionsFile(new FileInfo("TheApplicationConfig.java", "%s/TheApplicationConfig.java".formatted(root)), new RevisionScore(2));
        var revisions3 = new ClocRevisionsFile(new FileInfo("FeatureAComponent.java", "%s/featureA/FeatureAComponent.java".formatted(rootBoundedContexts)), new RevisionScore(1));
        var revisions4 = new ClocRevisionsFile(new FileInfo("FeatureAItem.java", "%s/featureA/FeatureAItem.java".formatted(rootBoundedContexts)), new RevisionScore(2));
        var revisions5 = new ClocRevisionsFile(new FileInfo("FeatureBComponent.java", "%s/featureB/FeatureBComponent.java".formatted(rootBoundedContexts)), new RevisionScore(1));
        var revisions6 = new ClocRevisionsFile(new FileInfo("FeatureBItem.java", "%s/featureB/FeatureBItem.java".formatted(rootBoundedContexts)), new RevisionScore(2));
        var revisions7 = new ClocRevisionsFile(new FileInfo("FeatureBComponent.java", "%s/featureB/refactored/FeatureBComponent.java".formatted(rootBoundedContexts)), new RevisionScore(2));
        var revisions8 = new ClocRevisionsFile(new FileInfo("FeatureBItem.java", "%s/featureB/refactored/FeatureBItem.java".formatted(rootBoundedContexts)), new RevisionScore(2));
        var revisions9 = new ClocRevisionsFile(new FileInfo("HexagonalArchitecture.java", "%s/HexagonalArchitecture.java".formatted(rootBoundedContexts)), new RevisionScore(2));
        sut = new ClusteredClocRevisions(
                List.of(revisions1, revisions2, revisions3, revisions4, revisions5, revisions6, revisions7, revisions8, revisions9));
    }

    public static Stream<Arguments> should_create_clusters_params() {
        var boundedContexts = "boundedcontexts";
        final String fullPrefix = "src/main/java/io.mystartup.project";
        final String prefix = "io.mystartup.project";
        return Stream.of(
                Arguments.of(boundedContexts),
                Arguments.of("/%s".formatted(boundedContexts)),
                Arguments.of("%s/".formatted(boundedContexts)),
                Arguments.of("/%s/".formatted(boundedContexts)),
                Arguments.of("%s/%s".formatted(fullPrefix, boundedContexts)),
                Arguments.of("%s/%s/".formatted(fullPrefix, boundedContexts)),
                Arguments.of("/%s/%s".formatted(prefix, boundedContexts)),
                Arguments.of("/%s/%s/".formatted(prefix, boundedContexts)),
                Arguments.of("%s/%s".formatted(prefix, boundedContexts)),
                Arguments.of("%s/%s/".formatted(prefix, boundedContexts))
        );
    }

    @ParameterizedTest
    @MethodSource("should_create_clusters_params")
    void should_create_clusters(String rootFolder) {
        var clusters = sut.toFileSystemTree(rootFolder);
        var expectedFsTree = new FileSystemTree("boundedcontexts");
        assertThat(clusters).isEqualTo(expectedFsTree);
    }

    @Test
    void should_create_clusters_from_default_root_folder() {
        var expectedFsTree = new FileSystemTree("boundedcontexts");
        var clusters = sut.toFileSystemTree("/");
        assertThat(clusters).isEqualTo(expectedFsTree);
    }
}
