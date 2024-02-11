package io.sch.historyscan.domain.contexts.analysis.network;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkNodesDeserializer extends JsonDeserializer<NetworkNodes> {

    @Override
    public NetworkNodes deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode nodesArray = rootNode.get("nodes");

        List<NetworkNode> networkNodes = new ArrayList<>();
        for (JsonNode node : nodesArray) {
            String name = node.get("name").asText();
            String path = node.get("path").asText();
            String parentPath = node.get("parentPath").asText();
            int currentNbLines = node.get("currentNbLines").asInt();
            int score = node.get("score").asInt();

            List<NetworkLink> links = new ArrayList<>();
            for (JsonNode linkNode : node.get("links")) {
                String linkPath = linkNode.get("path").asText();
                int weight = linkNode.get("weight").asInt();
                links.add(new NetworkLink(linkPath, weight));
            }

            networkNodes.add(new NetworkNode(name, path, parentPath, currentNbLines, score, links));
        }

        return new NetworkNodes(networkNodes);
    }
}
