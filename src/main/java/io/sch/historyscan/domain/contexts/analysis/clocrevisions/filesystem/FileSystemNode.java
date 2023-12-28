package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.RevisionScore;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@DDDEntity
public class FileSystemNode {
    private final String name;
    private final String path;
    private final boolean isFile;
    private RevisionScore score;
    private final Map<String, FileSystemNode> children;

    public FileSystemNode(String name, String path, boolean isFile, RevisionScore score) {
        this.name = name;
        this.path = path;
        this.isFile = isFile;
        this.score = score;
        this.children = new HashMap<>();
    }

    public void addChild(String name, FileSystemNode node) {
        children.put(name, node);
    }

    public FileSystemNode getChild(String path) {
        return children.get(path);
    }

    public boolean isFile() {
        return isFile;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Long getScore() {
        return score != null ? score.score() : null;
    }

    public Map<String, FileSystemNode> getChildren() {
        return children;
    }

    public void updateScoreFrom(int numberOfRevisions, int currentNbLines) {
        this.score = RevisionScore.of(numberOfRevisions, currentNbLines, score.score());
    }

    public void updateScoreFrom(RevisionScore score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileSystemNode that = (FileSystemNode) o;
        return isFile == that.isFile
                && Objects.equals(name, that.name)
                && Objects.equals(path, that.path)
                && Objects.equals(score, that.score)
                && Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path, isFile, score, children);
    }

    @Override
    public String toString() {
        return "FileSystemNode{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", isFile=" + isFile +
                ", score=" + score +
                ", children=" + children +
                '}';
    }

    public Optional<FileSystemNode> findFileNode(String path) {
        return findFileNode(this, path);
    }

    private Optional<FileSystemNode> findFileNode(FileSystemNode current, String path) {
        if (current.isFile && (current.getPath().contains(path))) {
            return Optional.of(current);
        } else {
            for (FileSystemNode child : current.getChildren().values()) {
                Optional<FileSystemNode> result = findFileNode(child, path);
                if (result.isPresent()) {
                    return result;
                }
            }
            return Optional.empty();
        }

    }
}
