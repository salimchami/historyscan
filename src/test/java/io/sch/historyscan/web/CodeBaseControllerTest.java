package io.sch.historyscan.web;

import io.sch.historyscan.common.HistoryscanIntegrationTests;
import io.sch.historyscan.common.JsonReader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.sch.historyscan.common.HistoryscanIntegrationTests.EndPoints.CODEBASE_ADD;
import static io.sch.historyscan.common.HistoryscanIntegrationTests.TestsFolders.CODEBASE_FOLDER;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CodeBaseControllerTest extends HistoryscanIntegrationTests {

    @Test
    void should_checkout_the_codebase_over_http() throws Exception {
        String expectedAddedCodebaseResponse = JsonReader.toExpectedJson(CODEBASE_FOLDER, "added-codebase");
        final String codebaseToAdd = JsonReader.toRequestedJson(CODEBASE_FOLDER, "http-codebase");
        endPointCaller.perform(post(CODEBASE_ADD).content(codebaseToAdd))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedAddedCodebaseResponse, true));
        checkCodebase("public-articles");
    }

    @Test
    @Disabled
    void currentCodeBases() {
    }
}