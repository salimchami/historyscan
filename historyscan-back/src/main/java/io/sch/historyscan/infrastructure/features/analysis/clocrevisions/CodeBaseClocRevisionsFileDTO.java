package io.sch.historyscan.infrastructure.features.analysis.clocrevisions;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class CodeBaseClocRevisionsFileDTO extends RepresentationModel<CodeBaseClocRevisionsFileDTO> {

    private final String name;
    private final String path;
    private final String parentPath;
    private final Long score;

    @JsonCreator
    public CodeBaseClocRevisionsFileDTO(String name, String path, String parentPath, Long score) {
        this.name = name;
        this.path = path;
        this.parentPath = parentPath;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Long getScore() {
        return score;
    }

    public String getParentPath() {
        return parentPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseClocRevisionsFileDTO that = (CodeBaseClocRevisionsFileDTO) o;
        return Objects.equals(name, that.name)
                && Objects.equals(parentPath, that.parentPath)
                && Objects.equals(path, that.path)
                && Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, parentPath, path, score);
    }
}
