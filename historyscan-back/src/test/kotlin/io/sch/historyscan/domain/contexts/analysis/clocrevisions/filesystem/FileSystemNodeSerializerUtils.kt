package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import io.sch.historyscan.common.*

object FileSystemNodeSerializerUtils {
    @Throws(JsonProcessingException::class)
    fun serializeExpectedRoot(expectedRootTestCase: String): FileSystemNode? {
        val expectedRootJson = JsonReader.toExpectedJson("filesystemtree", expectedRootTestCase)
        val mapper = ObjectMapper()
        val module = SimpleModule()
        module.addDeserializer(FileSystemNode::class.java, FileSystemNodeDeserializer())
        mapper.registerModule(module)
        return mapper.readValue(expectedRootJson, FileSystemNode::class.java)
    }
}
