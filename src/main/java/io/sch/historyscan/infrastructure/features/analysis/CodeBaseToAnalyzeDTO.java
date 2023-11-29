package io.sch.historyscan.infrastructure.features.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sch.historyscan.domain.contexts.analysis.common.EnumAnalysisType;

import java.util.Objects;

public class CodeBaseToAnalyzeDTO {
    private final String name;
    private final EnumAnalysisType analysisType;

    private final String rootFolder;

    @JsonCreator
    public CodeBaseToAnalyzeDTO(
            @JsonProperty("name") String name,
            @JsonProperty("analysis-type") EnumAnalysisType analysisType,
            @JsonProperty("root-folder") String rootFolder) {
        this.name = name;
        this.analysisType = analysisType;
        this.rootFolder = rootFolder;
    }

    public String getName() {
        return name;
    }

    public EnumAnalysisType getType() {
        return analysisType;
    }

    public String getRootFolder() {
        return rootFolder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeBaseToAnalyzeDTO that = (CodeBaseToAnalyzeDTO) o;
        return Objects.equals(name, that.name)
                && analysisType == that.analysisType
                && Objects.equals(rootFolder, that.rootFolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, analysisType, rootFolder);
    }
}
