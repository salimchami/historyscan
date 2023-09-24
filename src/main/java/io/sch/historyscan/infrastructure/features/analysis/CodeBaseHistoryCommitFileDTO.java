package io.sch.historyscan.infrastructure.features.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class CodeBaseHistoryCommitFileDTO extends RepresentationModel<CodeBaseHistoryCommitFileDTO> {

    private final String fileName;
    private final int nbLinesAdded;
    private final int nbLinesDeleted;

    @JsonCreator
    public CodeBaseHistoryCommitFileDTO(@JsonProperty("file-name") String fileName,
                                        @JsonProperty("nb-lines-added") int nbLinesAdded,
                                        @JsonProperty("nb-lines-deleted") int nbLinesDeleted) {
        this.fileName = fileName;
        this.nbLinesAdded = nbLinesAdded;
        this.nbLinesDeleted = nbLinesDeleted;
    }

    public String getFileName() {
        return fileName;
    }

    public int getNbLinesAdded() {
        return nbLinesAdded;
    }

    public int getNbLinesDeleted() {
        return nbLinesDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseHistoryCommitFileDTO that = (CodeBaseHistoryCommitFileDTO) o;
        return nbLinesAdded == that.nbLinesAdded && nbLinesDeleted == that.nbLinesDeleted && Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fileName, nbLinesAdded, nbLinesDeleted);
    }
}
