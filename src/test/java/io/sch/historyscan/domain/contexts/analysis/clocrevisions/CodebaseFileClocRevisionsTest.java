package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static io.sch.historyscan.fake.CodeBaseCommitFake.defaultHistory;
import static org.assertj.core.api.Assertions.assertThat;

class CodebaseFileClocRevisionsTest {

    public static Stream<Arguments> should_sort_revisions_params() {
        var file1Name = "file-1.java";
        var file2Name = "file-2.java";
        return Stream.of(
                Arguments.of(1, 1, List.of(file1Name, file2Name)),
                Arguments.of(1, 2, List.of(file2Name, file1Name)),
                Arguments.of(2, 1, List.of(file1Name, file2Name)),
                Arguments.of(2, 2, List.of(file1Name, file2Name))
        );
    }

    @ParameterizedTest
    @MethodSource("should_sort_revisions_params")
    void should_sort_revisions(int score1, int score2, List<String> expectedFiles) {
        var revisions1 = new CodebaseFileClocRevisions("file-1.java", 10, 1500, score1);
        var revisions2 = new CodebaseFileClocRevisions("file-2.java", 12, 20, score2);
        var sortedRevisions = Stream.of(revisions1, revisions2).sorted().toList();
        assertThat(sortedRevisions)
                .extracting(CodebaseFileClocRevisions::fileName)
                .isEqualTo(expectedFiles);
    }

    @Test
    void should_generate_cloc_revisions() {
        var commits = defaultHistory().commits();

        var revisions = CodebaseClocRevisions.of(commits);

        var expectedRevisions = List.of(
                new CodebaseFileClocRevisions("/boundedcontexts/featureA/file-1.java", 22, 20, 110),
                new CodebaseFileClocRevisions("/boundedcontexts/featureB/file-6.java", 22, 20, 110),
                new CodebaseFileClocRevisions("/boundedcontexts/single-file-1.java", 22, 20, 110),
                new CodebaseFileClocRevisions("/boundedcontexts/single-file-2.java", 22, 20, 110),
                new CodebaseFileClocRevisions("/boundedcontexts/featureB/file-4.java", 135, 150, 90),
                new CodebaseFileClocRevisions("/boundedcontexts/featureB/file-5.java", 222, 523, 42),
                new CodebaseFileClocRevisions("/boundedcontexts/featureA/file-2.java", 310, 1500, 21),
                new CodebaseFileClocRevisions("/boundedcontexts/featureA/file-3.java", 190, 1250, 15)
        );
        var expectedIgnoredRevisions = List.<CodebaseFileClocRevisions>of();
        var expectedExtensions = List.of("java");
        var expectedClocRevisions = new CodebaseClocRevisions(List.of(expectedRevisions), expectedIgnoredRevisions, expectedExtensions);
        assertThat(revisions).isEqualTo(expectedClocRevisions);
    }

    @Test
    void should_generate_cloc_revisions_based_on_base_folder() {
        var commits = defaultHistory().commits();

        var revisions = CodebaseClocRevisions.of(commits, "/boundedcontexts");

        var expectedRevisions = List.of(
                List.of(
                        new CodebaseFileClocRevisions("/single-file-1.java", 22, 20, 110),
                        new CodebaseFileClocRevisions("/single-file-2.java", 22, 20, 110)
                ),
                List.of(
                        new CodebaseFileClocRevisions("/featureB/file-6.java", 22, 20, 110),
                        new CodebaseFileClocRevisions("/featureB/file-4.java", 135, 150, 90),
                        new CodebaseFileClocRevisions("/featureB/file-5.java", 222, 523, 42)
                ),
                List.of(
                        new CodebaseFileClocRevisions("/featureA/file-1.java", 22, 20, 110),
                        new CodebaseFileClocRevisions("/featureA/file-2.java", 310, 1500, 21),
                        new CodebaseFileClocRevisions("/featureA/file-3.java", 190, 1250, 15)
                )
        );

        var expectedIgnoredRevisions = List.<CodebaseFileClocRevisions>of();
        var expectedExtensions = List.of("java");
        var expectedClocRevisions = new CodebaseClocRevisions(expectedRevisions, expectedIgnoredRevisions, expectedExtensions);
        assertThat(revisions).isEqualTo(expectedClocRevisions);
    }
}