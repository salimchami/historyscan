package io.sch.historyscan.domain.contexts.analysis.clocrevisions

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.RevisionScore.Companion.calculateRevisionsScore
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class RevisionScoreTest {
    @ParameterizedTest
    @MethodSource("should_calculate_revisions_score_params")
    fun should_calculate_revisions_score(numberOfRevisions: Int, nbLines: Int, expectedScore: Int) {
        val score = calculateRevisionsScore(numberOfRevisions, nbLines)
        Assertions.assertThat(score).isEqualTo(expectedScore.toLong())
    }

    companion object {
        fun should_calculate_revisions_score_params(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(22, 20, 440),
                Arguments.of(135, 150, 20250),
                Arguments.of(222, 523, 116106),
                Arguments.of(310, 1500, 465000),
                Arguments.of(190, 1250, 237500)
            )
        }
    }
}