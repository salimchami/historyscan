package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.RevisionScore;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileSystemTree {
    private final FileSystemNode root;
    private final RootFolder rootFolder;

    private final List<FileInfo> ignoredFiles;

    public FileSystemTree(RootFolder rootFolder) {
        this.rootFolder = Objects.requireNonNull(rootFolder);
        this.ignoredFiles = new ArrayList<>();
        root = new FileSystemNode("root", "/", null, false, new RevisionScore(0));
    }

    public void createFrom(CodeBaseFile codeBaseFile) {
        if (!codeBaseFile.hasSameNameAsCodeBase()) {
            throw new IllegalArgumentException("Codebase must be the same as the root folder name");
        }
        for (FileInfo nestedCodeBaseFile : codeBaseFile.children()) {
            addFile(nestedCodeBaseFile);
        }
        this.ignoredFiles.addAll(codeBaseFile.getIgnoredFiles());
    }

    private void addFile(FileInfo file) {
        final List<String> parts = file.pathParts();
        int partsIndexFromRootFolder = parts.indexOf(rootFolder.getValue());
        if (partsIndexFromRootFolder != -1) {
            addFileParts(parts, file, partsIndexFromRootFolder);
        }
    }

    private void addFileParts(List<String> codeBaseParts, FileInfo file, int partsIndexFromRootFolder) {
        var current = root;
        for (int i = partsIndexFromRootFolder; i < codeBaseParts.size(); i++) {
            String part = codeBaseParts.get(i);
            if (!current.getChildren().containsKey(part)) {
                var parentPath = parentPath(file, i, part);
                var newNode = new FileSystemNode(
                        part,
                        file.pathFrom(part),
                         parentPath,
                        file.isFileFrom(part),
                        new RevisionScore(0));
                current.addChild(part, newNode);
            }
            current = current.getChild(part);
        }
    }

    private String parentPath(FileInfo file, int i, String part) {
        if(rootFolder.getCodebaseName().equals(part)) {
            return "/";
        }
        return i == 0 ? null : file.parentPathFrom(part);
    }

    public FileSystemTree updateFilesScoreFrom(List<CodeBaseCommit> history) {
        for (CodeBaseCommit commit : history) {
            for (var file : commit.files()) {
                root.findFileNode(file.path())
                        .ifPresent(node -> node.updateScoreFrom(file.cloc(), file.currentNbLines()));
            }
        }
        return this;
    }

    public FileSystemTree then() {
        return this;
    }

    public FileSystemTree updateFoldersScore() {
        updateFolderScore(root);
        return this;
    }


    private long updateFolderScore(FileSystemNode node) {
        if (node.isFile()) {
            return node.getScore();
        } else {
            long totalScore = 0;
            for (FileSystemNode child : node.getChildren().values()) {
                totalScore += updateFolderScore(child);
            }
            node.updateScoreFrom(new RevisionScore(totalScore));
            return totalScore;
        }
    }

    public FileSystemNode getRoot() {
        return root;
    }

    public List<FileInfo> ignoredRevisions() {
        return ignoredFiles;
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
        return "FileSystemTree{" +
                "root=" + root +
                ", rootFolder=" + rootFolder +
                '}';
    }
}
