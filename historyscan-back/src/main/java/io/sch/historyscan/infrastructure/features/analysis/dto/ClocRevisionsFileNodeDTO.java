package io.sch.historyscan.infrastructure.features.analysis.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class ClocRevisionsFileNodeDTO extends RepresentationModel<ClocRevisionsFileNodeDTO> {
    private final String name;
    private final String path;
    private final String parentPath;
    private final boolean isFile;
    private final long currentNbLines;
    private final long score;
    private final List<ClocRevisionsFileNodeDTO> children;

    @JsonCreator
    public ClocRevisionsFileNodeDTO(String name, String path, String parentPath,
                                    @JsonProperty("isFile") boolean isFile, long currentNbLines, long score,
                                    List<ClocRevisionsFileNodeDTO> children) {
        this.name = name;
        this.path = path;
        this.parentPath = parentPath;
        this.isFile = isFile;
        this.currentNbLines = currentNbLines;
        this.score = score;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getParentPath() {
        return parentPath;
    }

    @JsonProperty("isFile")
    public boolean isFile() {
        return isFile;
    }

    public long getCurrentNbLines() {
        return currentNbLines;
    }

    public long getScore() {
        return score;
    }

    public List<ClocRevisionsFileNodeDTO> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClocRevisionsFileNodeDTO that = (ClocRevisionsFileNodeDTO) o;
        return isFile == that.isFile
                && currentNbLines == that.currentNbLines
                && score == that.score
                && Objects.equals(name, that.name)
                && Objects.equals(path, that.path)
                && Objects.equals(parentPath, that.parentPath)
                && Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, path, parentPath, isFile, currentNbLines, score, children);
    }
}
