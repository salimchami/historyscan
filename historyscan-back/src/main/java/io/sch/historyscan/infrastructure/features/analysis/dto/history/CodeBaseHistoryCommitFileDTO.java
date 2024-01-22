package io.sch.historyscan.infrastructure.features.analysis.dto.history;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class CodeBaseHistoryCommitFileDTO extends RepresentationModel<CodeBaseHistoryCommitFileDTO> {

    private final String fileName;
    private final String filePath;
    private final int nbActualLines;
    private final int nbAddedLines;
    private final int nbDeletedLines;
    private final int nbModifiedLines;

    @JsonCreator
    public CodeBaseHistoryCommitFileDTO(String fileName,
                                        String filePath,
                                        int nbActualLines,
                                        int nbAddedLines,
                                        int nbDeletedLines,
                                        int nbModifiedLines) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.nbActualLines = nbActualLines;
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

    public int getNbActualLines() {
        return nbActualLines;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseHistoryCommitFileDTO that = (CodeBaseHistoryCommitFileDTO) o;
        return nbActualLines == that.nbActualLines
                && nbAddedLines == that.nbAddedLines
                && nbDeletedLines == that.nbDeletedLines
                && nbModifiedLines == that.nbModifiedLines
                && Objects.equals(fileName, that.fileName)
                && Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                nbActualLines,
                nbAddedLines,
                nbDeletedLines,
                nbModifiedLines,
                fileName,
                filePath);
    }
}
