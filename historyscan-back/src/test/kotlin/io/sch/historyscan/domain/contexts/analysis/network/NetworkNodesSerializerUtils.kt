package io.sch.historyscan.domain.contexts.analysis.network

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import io.sch.historyscan.common.*

object NetworkNodesSerializerUtils {
    @Throws(JsonProcessingException::class)
    fun serializeExpected(expectedRootTestCase: String): NetworkNodes? {
        val expectedNetworkJson = JsonReader.toExpectedJson("analysis", expectedRootTestCase)
        val mapper = ObjectMapper()
        val module = SimpleModule()
        module.addDeserializer(NetworkNodes::class.java, NetworkNodesDeserializer())
        mapper.registerModule(module)
        return mapper.readValue(expectedNetworkJson, NetworkNodes::class.java)
    }
}
