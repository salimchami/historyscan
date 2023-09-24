package io.sch.historyscan.infrastructure.features.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class AnalyzedCodeBaseDTO extends RepresentationModel<AnalyzedCodeBaseDTO> {
    private final List<CodeBaseFileDTO> files;

    @JsonCreator
    public AnalyzedCodeBaseDTO(List<CodeBaseFileDTO> files) {
        this.files = files;
    }

    public List<CodeBaseFileDTO> getFiles() {
        return files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AnalyzedCodeBaseDTO that = (AnalyzedCodeBaseDTO) o;
        return Objects.equals(files, that.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), files);
    }
}
