package io.sch.historyscan.infrastructure.features.analysis

import io.sch.historyscan.common.*
import io.sch.historyscan.common.HistoryscanIntegrationTests.TestsFolders.ANALYSIS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.stream.Stream

internal class AnalysisControllerTest : HistoryscanIntegrationTests() {
    @ParameterizedTest
    @MethodSource("should_analyze_history_of_the_codebase_params")
    @Throws(
        Exception::class
    )
    fun should_analyze_the_codebase(testCase: String, expectedResult: String) {
        val requestedCodebaseToAnalyze = JsonReader.toRequestedJson(ANALYSIS, testCase)
        val expectedHistory = JsonReader.toExpectedJson(ANALYSIS, expectedResult)
        endPointCaller.perform(
            post("/api/v1/analyze").content(requestedCodebaseToAnalyze))
            ?.andExpect(MockMvcResultMatchers.status().isOk())
            ?.andExpect(MockMvcResultMatchers.content().json(expectedHistory, true))
    }

    companion object {
        @JvmStatic
        fun should_analyze_history_of_the_codebase_params(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("codebase-to-analyze-history", "codebase-history"),
                Arguments.of("codebase-to-analyze-cloc-revisions", "codebase-cloc-revisions"),
                Arguments.of("codebase-to-analyze-cloc-revisions-domain", "codebase-cloc-revisions-domain"),
                Arguments.of(
                    "codebase-to-analyze-cloc-revisions-domain-path",
                    "codebase-cloc-revisions-domain-with-path"
                ),
                Arguments.of("codebase-to-analyze-network", "codebase-network")
            )
        }
    }
}