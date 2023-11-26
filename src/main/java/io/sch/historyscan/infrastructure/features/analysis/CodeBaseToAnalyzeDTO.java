package io.sch.historyscan.infrastructure.features.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sch.historyscan.domain.contexts.analysis.common.EnumAnalysisType;

import java.util.Objects;

public class CodeBaseToAnalyzeDTO {
    private final String name;
    private final EnumAnalysisType analysisType;

    private final String baseFolder;

    @JsonCreator
    public CodeBaseToAnalyzeDTO(
            @JsonProperty("name") String name,
            @JsonProperty("analysis-type") EnumAnalysisType analysisType,
            @JsonProperty("base-folder") String baseFolder) {
        this.name = name;
        this.analysisType = analysisType;
        this.baseFolder = baseFolder;
    }

    public String getName() {
        return name;
    }

    public EnumAnalysisType getType() {
        return analysisType;
    }

    public String getBaseFolder() {
        return baseFolder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeBaseToAnalyzeDTO that = (CodeBaseToAnalyzeDTO) o;
        return Objects.equals(name, that.name) && analysisType == that.analysisType && Objects.equals(baseFolder, that.baseFolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, analysisType, baseFolder);
    }
}
