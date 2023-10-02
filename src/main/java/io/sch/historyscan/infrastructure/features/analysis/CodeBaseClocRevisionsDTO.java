package io.sch.historyscan.infrastructure.features.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class CodeBaseClocRevisionsDTO extends RepresentationModel<CodeBaseClocRevisionsDTO> {

    private final List<CodeBaseClocRevisionsFileDTO> revisions;
    private final List<CodeBaseClocRevisionsFileDTO> ignoredRevisions;

    @JsonCreator
    public CodeBaseClocRevisionsDTO(List<CodeBaseClocRevisionsFileDTO> revisions,
                                    List<CodeBaseClocRevisionsFileDTO> ignoredRevisions) {
        this.revisions = revisions;
        this.ignoredRevisions = ignoredRevisions;
    }

    public List<CodeBaseClocRevisionsFileDTO> getRevisions() {
        return revisions;
    }

    public List<CodeBaseClocRevisionsFileDTO> getIgnoredRevisions() {
        return ignoredRevisions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseClocRevisionsDTO that = (CodeBaseClocRevisionsDTO) o;
        return Objects.equals(revisions, that.revisions) && Objects.equals(ignoredRevisions, that.ignoredRevisions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), revisions, ignoredRevisions);
    }
}
