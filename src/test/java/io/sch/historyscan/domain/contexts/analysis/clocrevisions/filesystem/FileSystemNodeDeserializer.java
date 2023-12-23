package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.RevisionScore;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class FileSystemNodeDeserializer extends JsonDeserializer<FileSystemNode> {

    @Override
    public FileSystemNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String name = node.get("name").asText();
        String path = node.get("path").asText();
        boolean isFile = node.get("file").asBoolean();
        RevisionScore score = new RevisionScore(node.get("score").asInt());

        FileSystemNode fileSystemNode = new FileSystemNode(name, path, isFile, score);

        JsonNode childrenNode = node.get("children");
        Iterator<Map.Entry<String, JsonNode>> fields = childrenNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            FileSystemNode child = deserialize(field.getValue(), fileSystemNode);
            fileSystemNode.addChild(field.getKey(), child);
        }

        return fileSystemNode;
    }

    private FileSystemNode deserialize(JsonNode node, FileSystemNode parent) {
        String name = node.get("name").asText();
        String path = node.get("path").asText();
        boolean isFile = node.get("file").asBoolean();
        RevisionScore score = new RevisionScore(node.get("score").asInt());

        FileSystemNode fileSystemNode = new FileSystemNode(name, path, isFile, score);

        JsonNode childrenNode = node.get("children");
        Iterator<Map.Entry<String, JsonNode>> fields = childrenNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            FileSystemNode child = deserialize(field.getValue(), fileSystemNode);
            fileSystemNode.addChild(field.getKey(), child);
        }

        return fileSystemNode;
    }
}
