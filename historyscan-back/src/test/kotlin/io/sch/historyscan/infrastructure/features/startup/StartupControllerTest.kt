package io.sch.historyscan.infrastructure.features.startup

import io.sch.historyscan.common.*
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

internal class StartupControllerTest : HistoryscanIntegrationTests() {
    @Test
    @Throws(Exception::class)
    fun should_send_application_is_up_with_the_first_link() {
        val expectedStartupResponse = JsonReader.toExpectedJson(TestsFolders.APP_STARTUP_FOLDER, "startup")
        endPointCaller.perform(MockMvcRequestBuilders.get(EndPoints.BASE_URL))
            ?.andExpect(MockMvcResultMatchers.status().isOk())
            ?.andExpect(MockMvcResultMatchers.content().json(expectedStartupResponse, true))
    }
}