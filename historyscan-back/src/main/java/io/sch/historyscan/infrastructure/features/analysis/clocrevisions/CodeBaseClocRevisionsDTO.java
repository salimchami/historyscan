package io.sch.historyscan.infrastructure.features.analysis.clocrevisions;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class CodeBaseClocRevisionsDTO extends RepresentationModel<CodeBaseClocRevisionsDTO> {

    private final ClocRevisionsFileNodeDTO node;
    private final List<FileInfoDTO> ignoredRevisions;
    private final List<String> extensions;

    @JsonCreator
    public CodeBaseClocRevisionsDTO(
            ClocRevisionsFileNodeDTO node, List<FileInfoDTO> ignoredRevisions,
            List<String> extensions) {
        this.node = node;
        this.ignoredRevisions = ignoredRevisions;
        this.extensions = extensions;
    }

    public List<FileInfoDTO> getIgnoredRevisions() {
        return ignoredRevisions;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public ClocRevisionsFileNodeDTO getNode() {
        return node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseClocRevisionsDTO that = (CodeBaseClocRevisionsDTO) o;
        return Objects.equals(node, that.node) && Objects.equals(ignoredRevisions, that.ignoredRevisions) && Objects.equals(extensions, that.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), node, ignoredRevisions, extensions);
    }
}
