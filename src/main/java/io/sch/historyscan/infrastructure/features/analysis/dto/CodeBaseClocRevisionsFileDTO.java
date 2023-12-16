package io.sch.historyscan.infrastructure.features.analysis.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class CodeBaseClocRevisionsFileDTO extends RepresentationModel<CodeBaseClocRevisionsFileDTO> {

    private final String name;
    private final String path;
    private final long score;

    @JsonCreator
    public CodeBaseClocRevisionsFileDTO(String name, String path, long score) {
        this.name = name;
        this.path = path;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public long getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseClocRevisionsFileDTO that = (CodeBaseClocRevisionsFileDTO) o;
        return Objects.equals(name, that.name)
                && Objects.equals(path, that.path)
                && score == that.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, path, score);
    }
}
