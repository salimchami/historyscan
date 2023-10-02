package io.sch.historyscan.infrastructure.features.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class CodeBaseClocRevisionsDTO extends RepresentationModel<CodeBaseClocRevisionsDTO> {

    private final List<CodeBaseClocRevisionsFileDTO> revisions;

    @JsonCreator
    public CodeBaseClocRevisionsDTO(List<CodeBaseClocRevisionsFileDTO> revisions) {
        this.revisions = revisions;
    }

    public List<CodeBaseClocRevisionsFileDTO> getRevisions() {
        return revisions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseClocRevisionsDTO that = (CodeBaseClocRevisionsDTO) o;
        return Objects.equals(revisions, that.revisions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), revisions);
    }
}
