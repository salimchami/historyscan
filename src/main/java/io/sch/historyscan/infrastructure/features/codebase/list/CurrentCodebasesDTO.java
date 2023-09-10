package io.sch.historyscan.infrastructure.features.codebase.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class CurrentCodebasesDTO extends RepresentationModel<CurrentCodebasesDTO> {
    private final List<CurrentCodebaseDTO> codebases;

    @JsonCreator
    public CurrentCodebasesDTO(List<CurrentCodebaseDTO> codebases) {
        this.codebases = codebases;
    }

    public List<CurrentCodebaseDTO> getCodebases() {
        return codebases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CurrentCodebasesDTO that = (CurrentCodebasesDTO) o;
        return Objects.equals(codebases, that.codebases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), codebases);
    }
}
