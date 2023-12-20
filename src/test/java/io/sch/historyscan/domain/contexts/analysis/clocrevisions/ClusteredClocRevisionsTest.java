package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ClusteredClocRevisionsTest {

    public static Stream<Arguments> should_create_clusters_params() {
        var rootFolder = "boundedcontexts";
        final String rootFolderWithFullPrefix = "src/main/java/io.mystartup.project/%s".formatted(rootFolder);
        final String rootFolderWithPrefix = "io.mystartup.project/%s".formatted(rootFolder);
        return Stream.of(
                Arguments.of(rootFolder, rootFolder),
                Arguments.of("/%s".formatted(rootFolder), rootFolder),
                Arguments.of("%s/".formatted(rootFolder), rootFolder),
                Arguments.of("/%s/".formatted(rootFolder), rootFolder),
                Arguments.of(rootFolderWithFullPrefix, rootFolderWithFullPrefix),
                Arguments.of("src/main/java/io.mystartup.project/%s/".formatted(rootFolder), rootFolderWithFullPrefix),
                Arguments.of("/io.mystartup.project/%s".formatted(rootFolder), rootFolderWithPrefix),
                Arguments.of("/io.mystartup.project/%s/".formatted(rootFolder), rootFolderWithPrefix),
                Arguments.of("io.mystartup.project/%s".formatted(rootFolder), rootFolderWithPrefix),
                Arguments.of("io.mystartup.project/%s/".formatted(rootFolder), rootFolderWithPrefix)
        );
    }

    @ParameterizedTest
    @MethodSource("should_create_clusters_params")
    void should_create_clusters(String rootFolder, String expectedRootFolder) {
        var root = "src/main/java/io.mystartup.project/boundedcontexts";
        var revisions1 = new ClocRevisionsFile(new FileInfo("TheApplication.java", "%s/TheApplication.java".formatted(root)), new RevisionStats(1));
        var revisions2 = new ClocRevisionsFile(new FileInfo("TheApplicationConfig.java", "%s/TheApplicationConfig.java".formatted(root)), new RevisionStats(2));
        var revisions3 = new ClocRevisionsFile(new FileInfo("FeatureAComponent.java", "%s/featureA/FeatureAComponent.java".formatted(root)), new RevisionStats(1));
        var revisions4 = new ClocRevisionsFile(new FileInfo("FeatureAItem.java", "%s/featureA/FeatureAItem.java".formatted(root)), new RevisionStats(2));
        var revisions5 = new ClocRevisionsFile(new FileInfo("FeatureBComponent.java", "%s/featureB/FeatureBComponent.java".formatted(root)), new RevisionStats(1));
        var revisions6 = new ClocRevisionsFile(new FileInfo("FeatureBItem.java", "%s/featureB/FeatureBItem.java".formatted(root)), new RevisionStats(2));
        var revisions7 = new ClocRevisionsFile(new FileInfo("FeatureBComponent.java", "%s/featureB/refactored/FeatureBComponent.java".formatted(root)), new RevisionStats(2));
        var revisions8 = new ClocRevisionsFile(new FileInfo("FeatureBItem.java", "%s/featureB/refactored/FeatureBItem.java".formatted(root)), new RevisionStats(2));

        var sut = new ClusteredClocRevisions(
                List.of(revisions1, revisions2, revisions3, revisions4, revisions5, revisions6, revisions7, revisions8));

        var clusters = sut.toClusters(rootFolder);

        assertThat(clusters)
                .extracting(ClocRevisionsFileCluster::folder)
                .containsExactlyInAnyOrder(expectedRootFolder, "featureA", "featureB");
    }
}