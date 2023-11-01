package io.sch.historyscan.infrastructure.features.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class CodeBaseClocRevisionsFileDTO extends RepresentationModel<CodeBaseClocRevisionsFileDTO> {

    private final String name;
    private final long nbRevisions;

    @JsonCreator
    public CodeBaseClocRevisionsFileDTO(String name, long nbRevisions) {
        this.name = name;
        this.nbRevisions = nbRevisions;
    }

    public String getName() {
        return name;
    }

    public long getNbRevisions() {
        return nbRevisions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseClocRevisionsFileDTO that = (CodeBaseClocRevisionsFileDTO) o;
        return nbRevisions == that.nbRevisions && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, nbRevisions);
    }
}
