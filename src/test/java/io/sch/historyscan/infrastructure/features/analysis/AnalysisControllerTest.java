package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.common.HistoryscanIntegrationTests;
import io.sch.historyscan.common.JsonReader;
import org.junit.jupiter.api.Test;

import static io.sch.historyscan.common.HistoryscanIntegrationTests.TestsFolders.CODEBASE_FOLDER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AnalysisControllerTest extends HistoryscanIntegrationTests {

    @Test
    void should_analyze_history_of_the_codebase() throws Exception {
        String expectedHistory = JsonReader.toExpectedJson(CODEBASE_FOLDER, "codebase-history");
        endPointCaller.perform(get("/api/v1/analyze/public-articles-2/history"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedHistory, true));
    }

    @Test
    void should_analyze_cloc_and_revisions_of_the_codebase() throws Exception {
        String expectedHistory = JsonReader.toExpectedJson(CODEBASE_FOLDER, "codebase-cloc-revisions");
        endPointCaller.perform(get("/api/v1/analyze/public-articles/cloc-revisions"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedHistory, true));
    }
}