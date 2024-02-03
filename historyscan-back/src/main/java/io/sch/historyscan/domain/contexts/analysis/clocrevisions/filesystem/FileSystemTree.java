package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.RevisionScore;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@DDDEntity
public class FileSystemTree {
    private final FileSystemNode root;
    private final RootFolder rootFolder;
    private final List<FileInfo> ignoredFiles;

    public FileSystemTree(RootFolder rootFolder) {
        this.rootFolder = Objects.requireNonNull(rootFolder);
        this.ignoredFiles = new ArrayList<>();
        root = new FileSystemNode("root", "/", null, false, 0, new RevisionScore(0));
    }

    public void createFrom(CodeBaseFile codeBaseFile) {
        if (!codeBaseFile.hasSameNameAsCodeBase()) {
            throw new IllegalArgumentException("Codebase must be the same as the root folder name");
        }
        var filteredChildren = codeBaseFile.filteredChildren();
        for (var nestedCodeBaseFile : filteredChildren) {
            var parts = nestedCodeBaseFile.pathParts(rootFolder.getValue());
            addFileParts(parts, nestedCodeBaseFile);
        }
        this.ignoredFiles.addAll(codeBaseFile.getIgnoredFiles());
    }

    private void addFileParts(List<String> codeBaseParts, FileInfo file) {
        var current = root;
        for (var i = 0; i < codeBaseParts.size(); i++) {
            var part = codeBaseParts.get(i);
            if (!current.getChildren().containsKey(part)) {
                var parentPath = parentPath(file, i, part);
                var isFile = file.isFileFrom(part, rootFolder.getValue());
                var newNode = new FileSystemNode(
                        part,
                        file.pathFrom(part),
                        parentPath,
                        isFile,
                        isFile ? file.currentNbLines() : 0,
                        new RevisionScore(0));
                current.addChild(part, newNode);
            }
            current = current.findChild(part);
        }
    }

    private String parentPath(FileInfo file, int i, String part) {
        if (file.path().lastIndexOf(part) == 0 || rootFolder.getValue().equals(part)) {
            return "/";
        }
        return i == 0 ? null : file.parentPathFrom(part);
    }

    public FileSystemTree updateFilesScoreFrom(List<CodeBaseCommit> history) {
        history.stream()
                .flatMap(commit -> commit.files().stream())
                .collect(Collectors.groupingBy(CodeBaseHistoryCommitFile::fileInfo))
                .forEach((key, value) -> {
                    var revisions = value.stream()
                            .map(CodeBaseHistoryCommitFile::cloc)
                            .reduce(0, Integer::sum);
                    root.findFileNode(key.path())
                            .ifPresent(node -> node.updateScoreFrom(revisions));
                });
        return this;
    }

    public FileSystemTree then() {
        return this;
    }

    public FileSystemTree updateFoldersScore() {
        root.updateFolderScore();
        return this;
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
        var that = (FileSystemTree) o;
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

    public List<FileSystemNode> files() {
        return root.allFileNodes();
    }
}
