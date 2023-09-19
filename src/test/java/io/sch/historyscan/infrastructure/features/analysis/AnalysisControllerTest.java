package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.common.HistoryscanIntegrationTests;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AnalysisControllerTest extends HistoryscanIntegrationTests {

    @Test
    void should_analyze_history_of_the_codebase() throws Exception {
        endPointCaller.perform(post("/analyze/public-articles/history"))
                .andExpect(status().isCreated());
    }
}