package io.sch.historyscan.infrastructure.features.analysis.dto;

import java.util.List;

public class CodeBaseClocRevisionsFileClusterDTO {

    private final String folder;
    private final List<CodeBaseClocRevisionsFileDTO> children;

    public CodeBaseClocRevisionsFileClusterDTO(String folder, List<CodeBaseClocRevisionsFileDTO> children) {
        this.folder = folder;
        this.children = children;
    }

    public String getFolder() {
        return folder;
    }

    public List<CodeBaseClocRevisionsFileDTO> getChildren() {
        return children;
    }
}
