package io.sch.historyscan.infrastructure.features.codebase.info;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sch.historyscan.domain.contexts.analysis.common.EnumAnalysisType;
import io.sch.historyscan.domain.error.HistoryScanTechnicalException;
import io.sch.historyscan.infrastructure.features.analysis.AnalysisController;
import io.sch.historyscan.infrastructure.features.analysis.CodeBaseToAnalyzeDTO;
import io.sch.historyscan.infrastructure.features.codebase.CodeBaseController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CodebaseDTO extends RepresentationModel<CodebaseDTO> {
    private final String name;
    private final String url;
    private final String currentBranch;

    @JsonCreator
    public CodebaseDTO(String name, String url,
                       @JsonProperty("currentBranch") String currentBranch) {
        this.name = name;
        this.url = url;
        this.currentBranch = currentBranch;
        addSelfLink();
        addAnalysisLink();
    }

    private void addAnalysisLink() {
        Arrays.stream(EnumAnalysisType.values()).forEach(analysisType ->
        {
            try {
                add(linkTo(AnalysisController.class, AnalysisController.class.getMethod("analyze", CodeBaseToAnalyzeDTO.class))
                        .withRel("analyze-" + analysisType.getTitle())
                        .withTitle(HttpMethod.GET.name()));
            } catch (NoSuchMethodException e) {
                throw new HistoryScanTechnicalException("Error while adding analysis link", e);
            }
        });
    }

    private void addSelfLink() {
        add(linkTo(methodOn(CodeBaseController.class).findCodeBase(name))
                .withSelfRel()
                .withTitle(HttpMethod.GET.name()));
    }

    public String getCurrentBranch() {
        return currentBranch;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodebaseDTO that = (CodebaseDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, url);
    }
}
