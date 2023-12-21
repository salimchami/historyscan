package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import java.util.ArrayList;
import java.util.List;

public record  FileSystemNodeExtension(FileSystemNode root) {

    public List<String> getAllExtensions() {
        List<String> extensions = new ArrayList<>();
        collectExtensions(root, extensions);
        return extensions;
    }

    private void collectExtensions(FileSystemNode node, List<String> extensions) {
        if (node.isFile()) {
            String extension = getExtension(node.getName());
            if (extension != null) {
                extensions.add(extension);
            }
        } else {
            for (FileSystemNode child : node.getChildren().values()) {
                collectExtensions(child, extensions);
            }
        }
    }

    private String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1 && index < fileName.length() - 1) {
            return fileName.substring(index + 1);
        }
        return null;
    }
}
