package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.RevisionScore;

import java.io.IOException;
import java.util.Optional;

public class FileSystemNodeDeserializer extends JsonDeserializer<FileSystemNode> {

    @Override
    public FileSystemNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        var name = node.get("name").asText();
        var path = node.get("path").asText();
        var currentNbLines = node.get("currentNbLines").asInt();
        var parentPath = Optional.ofNullable(node.get("parentPath")).map(JsonNode::asText).orElse(null);
        var isFile = node.get("file").asBoolean();
        var score = new RevisionScore(node.get("score").asInt());

        var fileSystemNode = new FileSystemNode(name, path, parentPath, isFile, currentNbLines, score);

        var childrenNode = node.get("children");
        var fields = childrenNode.fields();
        while (fields.hasNext()) {
            var field = fields.next();
            var child = deserialize(field.getValue());
            fileSystemNode.addChild(field.getKey(), child);
        }

        return fileSystemNode;
    }

    private FileSystemNode deserialize(JsonNode node) {
        var name = node.get("name").asText();
        var path = node.get("path").asText();
        var currentNbLines = node.get("currentNbLines").asInt();
        var parentPath = Optional.ofNullable(node.get("parentPath")).map(JsonNode::asText).orElse(null);
        var isFile = node.get("file").asBoolean();
        var score = new RevisionScore(node.get("score").asInt());

        var fileSystemNode = new FileSystemNode(name, path, parentPath, isFile, currentNbLines, score);

        var childrenNode = node.get("children");
        var fields = childrenNode.fields();
        while (fields.hasNext()) {
            var field = fields.next();
            var child = deserialize(field.getValue());
            fileSystemNode.addChild(field.getKey(), child);
        }

        return fileSystemNode;
    }
}
