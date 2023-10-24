package io.sch.historyscan.infrastructure.features.analysis;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class CodeBaseClusteredClocRevisionsDTO extends RepresentationModel<CodeBaseClusteredClocRevisionsDTO> {
    private final List<List<CodeBaseClocRevisionsFileDTO>> revisions;
    private final List<CodeBaseClocRevisionsFileDTO> ignoredRevisions;
    private final List<String> extensions;

    public CodeBaseClusteredClocRevisionsDTO(List<List<CodeBaseClocRevisionsFileDTO>> revisions, List<CodeBaseClocRevisionsFileDTO> ignoredRevisions, List<String> extensions) {
        this.revisions = revisions;
        this.ignoredRevisions = ignoredRevisions;
        this.extensions = extensions;
    }

    public List<List<CodeBaseClocRevisionsFileDTO>> getRevisions() {
        return revisions;
    }

    public List<CodeBaseClocRevisionsFileDTO> getIgnoredRevisions() {
        return ignoredRevisions;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseClusteredClocRevisionsDTO that = (CodeBaseClusteredClocRevisionsDTO) o;
        return Objects.equals(revisions, that.revisions) && Objects.equals(ignoredRevisions, that.ignoredRevisions) && Objects.equals(extensions, that.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), revisions, ignoredRevisions, extensions);
    }
}
