package io.sch.historyscan.common

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder

@JvmRecord
data class EndPointCaller(val mockMvc: MockMvc?) {
    @Throws(Exception::class)
    fun perform(request: MockHttpServletRequestBuilder?): ResultActions? {
        return mockMvc!!.perform(
            request!!.accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
    }
}
