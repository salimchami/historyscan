package io.sch.historyscan.infrastructure.features.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class CodeBaseHistoryDTO extends RepresentationModel<CodeBaseHistoryDTO> {
    private final List<CodeBaseHistoryFileDTO> files;

    @JsonCreator
    public CodeBaseHistoryDTO(List<CodeBaseHistoryFileDTO> files) {
        this.files = files;
    }

    public List<CodeBaseHistoryFileDTO> getFiles() {
        return files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseHistoryDTO that = (CodeBaseHistoryDTO) o;
        return Objects.equals(files, that.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), files);
    }
}
