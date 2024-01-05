package io.sch.historyscan.infrastructure.features.analysis.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class CodeBaseClocRevisionsDTO extends RepresentationModel<CodeBaseClocRevisionsDTO> {

    private final ClocRevisionsFileTreeDTO filesTree;
    private final List<FileInfoDTO> ignoredRevisions;
    private final List<String> extensions;

    @JsonCreator
    public CodeBaseClocRevisionsDTO(
            ClocRevisionsFileTreeDTO filesTree, List<FileInfoDTO> ignoredRevisions,
            List<String> extensions) {
        this.filesTree = filesTree;
        this.ignoredRevisions = ignoredRevisions;
        this.extensions = extensions;
    }

    public List<FileInfoDTO> getIgnoredRevisions() {
        return ignoredRevisions;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public ClocRevisionsFileTreeDTO getFilesTree() {
        return filesTree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseClocRevisionsDTO that = (CodeBaseClocRevisionsDTO) o;
        return Objects.equals(filesTree, that.filesTree) && Objects.equals(ignoredRevisions, that.ignoredRevisions) && Objects.equals(extensions, that.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), filesTree, ignoredRevisions, extensions);
    }
}
