package io.sch.historyscan.infrastructure.features.analysis.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class CodeBaseClocRevisionsFileDTO extends RepresentationModel<CodeBaseClocRevisionsFileDTO> {

    private final String name;
    private final String path;
    private final String parentName;
    private final Long score;

    @JsonCreator
    public CodeBaseClocRevisionsFileDTO(String name, String parentName, String path, Long score) {
        this.name = name;
        this.parentName = parentName;
        this.path = path;
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

    public String getParentName() {
        return parentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseClocRevisionsFileDTO that = (CodeBaseClocRevisionsFileDTO) o;
        return Objects.equals(name, that.name)
                && Objects.equals(parentName, that.parentName)
                && Objects.equals(path, that.path)
                && Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, parentName, path, score);
    }
}
