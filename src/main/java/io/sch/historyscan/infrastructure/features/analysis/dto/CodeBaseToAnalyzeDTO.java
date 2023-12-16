package io.sch.historyscan.infrastructure.features.analysis.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public class CodeBaseToAnalyzeDTO {
    private final String name;
    private final String analysisType;

    @JsonCreator
    public CodeBaseToAnalyzeDTO(
            String name,
            String analysisType) {
        this.name = name;
        this.analysisType = analysisType;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return analysisType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeBaseToAnalyzeDTO that = (CodeBaseToAnalyzeDTO) o;
        return Objects.equals(name, that.name)
                && analysisType.equals(that.analysisType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, analysisType);
    }
}
