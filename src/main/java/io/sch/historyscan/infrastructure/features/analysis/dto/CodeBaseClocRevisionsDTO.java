package io.sch.historyscan.infrastructure.features.analysis.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sch.historyscan.domain.contexts.analysis.common.FileInfo;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class CodeBaseClocRevisionsDTO extends RepresentationModel<CodeBaseClocRevisionsDTO> {

    private final List<CodeBaseClocRevisionsFileDTO> revisions;
    private final List<FileInfo> ignoredRevisions;
    private final List<String> extensions;

    @JsonCreator
    public CodeBaseClocRevisionsDTO(
            List<CodeBaseClocRevisionsFileDTO> revisions,
            List<FileInfo> ignoredRevisions,
            List<String> extensions) {
        this.revisions = revisions;
        this.ignoredRevisions = ignoredRevisions;
        this.extensions = extensions;
    }

    public List<FileInfo> getIgnoredRevisions() {
        return ignoredRevisions;
    }

    public List<String> getExtensions() {
        return extensions;
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
        return Objects.equals(revisions, that.revisions)
                && Objects.equals(ignoredRevisions, that.ignoredRevisions)
                && Objects.equals(extensions, that.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), revisions, ignoredRevisions, extensions);
    }
}
