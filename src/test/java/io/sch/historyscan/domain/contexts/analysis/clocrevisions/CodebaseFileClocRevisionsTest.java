package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

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
    void should_sort_revisions(int filename1Revisions, int filename2Revisions, List<String> expectedFiles) {
        var revisions1 = new CodebaseFileClocRevisions("file-1.java", filename1Revisions, 1500);
        var revisions2 = new CodebaseFileClocRevisions("file-2.java", filename2Revisions, 20);
        var sortedRevisions = Stream.of(revisions1, revisions2).sorted().toList();
        assertThat(sortedRevisions)
                .extracting(CodebaseFileClocRevisions::fileName)
                .isEqualTo(expectedFiles);
    }
}