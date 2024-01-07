package io.sch.historyscan.infrastructure.features.startup;

import io.sch.historyscan.common.HistoryscanIntegrationTests;
import io.sch.historyscan.common.JsonReader;
import org.junit.jupiter.api.Test;

import static io.sch.historyscan.common.HistoryscanIntegrationTests.EndPoints.BASE_URL;
import static io.sch.historyscan.common.HistoryscanIntegrationTests.TestsFolders.APP_STARTUP_FOLDER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StartupControllerTest extends HistoryscanIntegrationTests {

    @Test
    void should_send_application_is_up_with_the_first_link() throws Exception {
        String expectedStartupResponse = JsonReader.toExpectedJson(APP_STARTUP_FOLDER, "startup");
        endPointCaller.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedStartupResponse, true));
    }
}