package io.sch.historyscan.infrastructure.features.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class CodeBaseHistoryFileDTO extends RepresentationModel<CodeBaseHistoryFileDTO> {
    private final CodeBaseHistoryCommitInfoDTO info;
    private final List<CodeBaseHistoryCommitFileDTO> files;

    @JsonCreator
    public CodeBaseHistoryFileDTO(CodeBaseHistoryCommitInfoDTO info, List<CodeBaseHistoryCommitFileDTO> files) {
        this.info = info;
        this.files = files;
    }

    public CodeBaseHistoryCommitInfoDTO getInfo() {
        return info;
    }

    public List<CodeBaseHistoryCommitFileDTO> getFiles() {
        return files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseHistoryFileDTO that = (CodeBaseHistoryFileDTO) o;
        return Objects.equals(info, that.info) && Objects.equals(files, that.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), info, files);
    }
}
