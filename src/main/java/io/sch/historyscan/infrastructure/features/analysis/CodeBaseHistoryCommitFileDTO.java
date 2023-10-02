package io.sch.historyscan.infrastructure.features.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class CodeBaseHistoryCommitFileDTO extends RepresentationModel<CodeBaseHistoryCommitFileDTO> {

    private final String fileName;
    private final int nbAddedLines;
    private final int nbDeletedLines;
    private final int nbModifiedLines;

    @JsonCreator
    public CodeBaseHistoryCommitFileDTO(@JsonProperty("file-name") String fileName,
                                        @JsonProperty("nb-lines-added") int nbAddedLines,
                                        @JsonProperty("nb-lines-deleted") int nbDeletedLines, int nbModifiedLines) {
        this.fileName = fileName;
        this.nbAddedLines = nbAddedLines;
        this.nbDeletedLines = nbDeletedLines;
        this.nbModifiedLines = nbModifiedLines;
    }

    public String getFileName() {
        return fileName;
    }

    public int getNbAddedLines() {
        return nbAddedLines;
    }

    public int getNbDeletedLines() {
        return nbDeletedLines;
    }

    public int getNbModifiedLines() {
        return nbModifiedLines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseHistoryCommitFileDTO that = (CodeBaseHistoryCommitFileDTO) o;
        return nbAddedLines == that.nbAddedLines && nbDeletedLines == that.nbDeletedLines && nbModifiedLines == that.nbModifiedLines && Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fileName, nbAddedLines, nbDeletedLines, nbModifiedLines);
    }
}
