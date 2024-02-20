package io.sch.historyscan.domain.contexts.analysis.network

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import java.io.IOException

class NetworkNodesDeserializer : JsonDeserializer<NetworkNodes>() {
    @Throws(IOException::class)
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): NetworkNodes {
        val rootNode = jsonParser.codec.readTree<JsonNode>(jsonParser)
        val nodesArray = rootNode["nodes"]

        val networkNodes: MutableList<NetworkNode> = ArrayList()
        for (node in nodesArray) {
            val name = node["name"].asText()
            val path = node["path"].asText()
            val parentPath = node["parentPath"].asText()
            val currentNbLines = node["currentNbLines"].asInt()
            val score = node["score"].asInt()

            val links: MutableList<NetworkLink> = ArrayList()
            for (linkNode in node["links"]) {
                val linkPath = linkNode["path"].asText()
                val weight = linkNode["weight"].asInt()
                links.add(NetworkLink(linkPath, weight.toLong()))
            }

            networkNodes.add(NetworkNode(name, path, parentPath, currentNbLines.toLong(), score.toLong(), links))
        }

        return NetworkNodes(networkNodes)
    }
}
