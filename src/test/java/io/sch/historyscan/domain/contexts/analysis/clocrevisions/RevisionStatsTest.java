package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RevisionStatsTest {

    public static Stream<Arguments> should_calculate_revisions_score_params() {
        return Stream.of(
                Arguments.of(22, 20, 440),
                Arguments.of(135, 150, 20250),
                Arguments.of(222, 523, 116106),
                Arguments.of(310, 1500, 465000),
                Arguments.of(190, 1250, 237500)
        );
    }

    @ParameterizedTest
    @MethodSource("should_calculate_revisions_score_params")
    void should_calculate_revisions_score(int numberOfRevisions, int nbLines, int expectedScore) {
        var score = RevisionStats.calculateRevisionsScore(numberOfRevisions, nbLines);
        assertThat(score).isEqualTo(expectedScore);
    }
}