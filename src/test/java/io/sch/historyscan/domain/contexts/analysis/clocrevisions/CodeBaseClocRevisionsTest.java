package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static io.sch.historyscan.fake.CodeBaseCommitFake.defaultHistory;
import static io.sch.historyscan.fake.FileInfoFake.*;
import static io.sch.historyscan.fake.FileNameFake.fileName1;
import static io.sch.historyscan.fake.FileNameFake.fileName2;
import static io.sch.historyscan.fake.RevisionsStatsFake.*;
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
        var revisions1 = new ClocRevisionsFile(file1, new RevisionStats(10, 1500, score1));
        var revisions2 = new ClocRevisionsFile(file2, new RevisionStats(12, 20, score2));
        var sortedRevisions = Stream.of(revisions1, revisions2).sorted().toList();
        assertThat(sortedRevisions)
                .extracting(ClocRevisionsFile::fileName)
                .isEqualTo(expectedFiles);
    }

    @Test
    void should_generate_cloc_revisions_based_on_base_folder() {
        var commits = defaultHistory().commits();

        var revisions = CodebaseClocRevisions.of(commits);

        var expectedRevisions = List.of(
                List.of(
                        new ClocRevisionsFile(singleFile1, singleFile1Stats),
                        new ClocRevisionsFile(singleFile2, singleFile2Stats)
                ),
                List.of(
                        new ClocRevisionsFile(file2, file2Stats),
                        new ClocRevisionsFile(file3, file3Stats),
                        new ClocRevisionsFile(file1, file1Stats)
                ),
                List.of(
                        new ClocRevisionsFile(file5, file5Stats),
                        new ClocRevisionsFile(file4, file4Stats),
                        new ClocRevisionsFile(file6, file6Stats))
        );

        var expectedClocRevisions = new CodebaseClocRevisions(expectedRevisions, List.of(), List.of("java"));
        assertThat(revisions).isEqualTo(expectedClocRevisions);
    }
}