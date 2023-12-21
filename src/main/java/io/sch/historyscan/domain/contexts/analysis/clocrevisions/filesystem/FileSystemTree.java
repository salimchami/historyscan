package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.ClocRevisionsFile;

import java.util.List;
import java.util.Objects;

public class FileSystemTree {
    private final FileSystemNode root;
    private final String rootFolder;

    public FileSystemTree(String rootFolder) {
        this.rootFolder = rootFolder;
        root = new FileSystemNode("/", "/", false, null);
    }

    public void addFile(ClocRevisionsFile file) {
        if (file.filePath().contains(rootFolder)) {
            var parts = file.filePath().split("/");
            var current = root;
            for (String part : parts) {
                if (!part.isEmpty()) {
                    if (!current.getChildren().containsKey(part)) {
                        var newNode = new FileSystemNode(part, file.pathFrom(part), isFile(file.filePath(), part), file.revisionScore());
                        current.addChild(part, newNode);
                    }
                    current = current.getChild(part);
                }
            }
        }
    }

    private boolean isFile(String path, String part) {
        String[] parts = path.split("/");
        String lastPart = parts[parts.length - 1];
        return lastPart.equals(part) && lastPart.contains(".");
    }

    public FileSystemNode getRoot() {
        return root;
    }

    public void updateFoldersScore() {

    }

    public List<ClocRevisionsFile> ignoredRevisions() {
        return List.of();
    }

    public List<String> extensions() {
        return new FileSystemNodeExtension(root).getAllExtensions();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileSystemTree that = (FileSystemTree) o;
        return Objects.equals(root, that.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root);
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
