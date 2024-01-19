package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.common.HistoryscanIntegrationTests;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.sch.historyscan.common.HistoryscanIntegrationTests.TestsFolders.CODEBASE_FOLDER;
import static io.sch.historyscan.common.JsonReader.toExpectedJson;
import static io.sch.historyscan.common.JsonReader.toRequestedJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AnalysisControllerTest extends HistoryscanIntegrationTests {
    public static Stream<Arguments> should_analyze_history_of_the_codebase_params() {
        return Stream.of(
//                Arguments.of("codebase-to-analyze-history", "codebase-history"),
                Arguments.of("codebase-to-analyze-cloc-revisions", "codebase-cloc-revisions")
//                Arguments.of("codebase-to-analyze-cloc-revisions-domain", "codebase-cloc-revisions-domain"),
//                Arguments.of("codebase-to-analyze-cloc-revisions-domain-path", "codebase-cloc-revisions-domain-with-path")
        );
    }

    @ParameterizedTest
    @MethodSource("should_analyze_history_of_the_codebase_params")
    void should_analyze_history_of_the_codebase(String testCase, String expectedResult) throws Exception {
        String requestedCodebaseToAnalyze = toRequestedJson(CODEBASE_FOLDER, testCase);
        String expectedHistory = toExpectedJson(CODEBASE_FOLDER, expectedResult);
        endPointCaller.perform(post("/api/v1/analyze")
                        .content(requestedCodebaseToAnalyze))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedHistory, true));
    }
}