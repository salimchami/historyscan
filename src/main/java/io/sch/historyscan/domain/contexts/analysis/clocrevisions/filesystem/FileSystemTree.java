package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.ClocRevisionsFile;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.RevisionScore;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.empty;

public class FileSystemTree {
    private final FileSystemNode root;
    private final RootFolder rootFolder;

    public FileSystemTree(RootFolder rootFolder) {
        this.rootFolder = rootFolder;
        root = new FileSystemNode("root", null, false, new RevisionScore(0));
    }

    public void addFileNodes(File file, String codeBaseName) {
        if (!file.getName().equals(codeBaseName)) {
            throw new IllegalArgumentException("Codebase must be the same as the root folder name");
        }
        if (!file.getName().equals(".git")) {
            try (var paths = Files.walk(file.toPath())) {
                for (File child : paths.map(Path::toFile).toList()) {
                    if (rootFolder.isIn(child)) {
                        addFile(child, codeBaseName);
                    }
                }
            } catch (IOException e) {
                throw new CodebasePathCanNotBeReadException("Error while reading codebase files tree", e);
            }
        }
    }

    private void addFile(File file, String codeBaseName) {
        String fullPath = file.getAbsolutePath();
        int index = fullPath.indexOf(codeBaseName);
        if (index != -1) {
            String path = fullPath.substring(index).replace("\\", "/");
            var parts = path.split("/");
            var current = root;
            int rootIndex = java.util.Arrays.asList(parts).indexOf(rootFolder.value());
            if (rootIndex != -1) {
                for (int i = rootIndex; i < parts.length; i++) {
                    String part = parts[i];
                    if (!part.isEmpty()) {
                        if (!current.getChildren().containsKey(part)) {
                            var newNode = new FileSystemNode(part, path, file.isFile(), new RevisionScore(0));
                            current.addChild(part, newNode);
                        }
                        current = current.getChild(part);
                    }
                }
            }
        }
    }

    public void updateScoreFrom(List<CodeBaseCommit> history) {
        for (CodeBaseCommit commit : history) {
            for (var file : commit.files()) {
                findNode(file.path()).ifPresent(node -> node.updateScoreFrom(file.cloc(), file.currentNbLines()));
            }
        }
    }

    private Optional<FileSystemNode> findNode(String path) {
        String[] parts = path.split("/");
        FileSystemNode current = root;
        for (String part : parts) {
            if (!part.isEmpty()) {
                current = current.getChild(part);
                if (current == null) {
                    return empty();
                }
            }
        }
        return Optional.of(current);
    }

    public FileSystemNode getRoot() {
        return root;
    }

    public List<ClocRevisionsFile> ignoredRevisions() {
        return List.of();
    }

    public List<String> extensions() {
        return new FileSystemNodeExtension(root)
                .getAllExtensions();
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
