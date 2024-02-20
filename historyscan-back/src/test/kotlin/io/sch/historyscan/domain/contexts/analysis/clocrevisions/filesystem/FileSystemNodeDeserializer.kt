package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.RevisionScore
import java.io.IOException
import java.util.*

class FileSystemNodeDeserializer : JsonDeserializer<FileSystemNode?>() {
    @Throws(IOException::class)
    override fun deserialize(
        jsonParser: JsonParser?,
        deserializationContext: DeserializationContext?
    ): FileSystemNode? {
        val node = jsonParser!!.codec.readTree<JsonNode?>(jsonParser)
        val name = node!!["name"].asText()
        val path = node["path"].asText()
        val currentNbLines = node["currentNbLines"].asInt()
        val parentPath = Optional.ofNullable(node["parentPath"]).map { obj: JsonNode? -> obj!!.asText() }
            .orElse(null)
        val isFile = node["file"].asBoolean()
        val score = RevisionScore(node["score"].asInt().toLong())

        val fileSystemNode = FileSystemNode(
            name!!, path!!, parentPath, isFile, currentNbLines.toLong(), score
        )

        val childrenNode = node["children"]
        val fields = childrenNode!!.fields()
        while (fields!!.hasNext()) {
            val field = fields.next()
            val child = deserialize(
                field!!.value
            )
            fileSystemNode.addChild(field.key!!, child!!)
        }

        return fileSystemNode
    }

    private fun deserialize(node: JsonNode?): FileSystemNode? {
        val name = node!!["name"].asText()
        val path = node["path"].asText()
        val currentNbLines = node["currentNbLines"].asInt()
        val parentPath = Optional.ofNullable(node["parentPath"]).map { obj: JsonNode? -> obj!!.asText() }
            .orElse(null)
        val isFile = node["file"].asBoolean()
        val score = RevisionScore(node["score"].asInt().toLong())

        val fileSystemNode = FileSystemNode(
            name!!, path!!, parentPath, isFile, currentNbLines.toLong(), score
        )

        val childrenNode = node["children"]
        val fields = childrenNode!!.fields()
        while (fields!!.hasNext()) {
            val field = fields.next()
            val child = deserialize(
                field!!.value
            )
            fileSystemNode.addChild(field.key!!, child!!)
        }

        return fileSystemNode
    }
}
