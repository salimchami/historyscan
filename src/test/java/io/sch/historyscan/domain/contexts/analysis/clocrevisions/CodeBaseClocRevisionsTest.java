package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static io.sch.historyscan.fake.FileInfoFake.file1;
import static io.sch.historyscan.fake.FileInfoFake.file2;
import static io.sch.historyscan.fake.FileNameFake.fileName1;
import static io.sch.historyscan.fake.FileNameFake.fileName2;
import static org.assertj.core.api.Assertions.assertThat;

class CodeBaseClocRevisionsTest {

    public static Stream<Arguments> should_sort_revisions_params() {
        return Stream.of(
                Arguments.of(1, 1, List.of(fileName1, fileName2)),
                Arguments.of(1, 2, List.of(fileName2, fileName1)),
                Arguments.of(2, 1, List.of(fileName1, fileName2)),
                Arguments.of(2, 2, List.of(fileName1, fileName2))
        );
    }

    @ParameterizedTest
    @MethodSource("should_sort_revisions_params")
    void should_sort_revisions(int score1, int score2, List<String> expectedFiles) {
        var revisions1 = new ClocRevisionsFile(file1, new RevisionScore(score1));
        var revisions2 = new ClocRevisionsFile(file2, new RevisionScore(score2));
        var sortedRevisions = Stream.of(revisions1, revisions2).sorted().toList();
        assertThat(sortedRevisions)
                .extracting(ClocRevisionsFile::fileName)
                .isEqualTo(expectedFiles);
    }


}