package io.sch.historyscan.infrastructure.features.codebase.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sch.historyscan.domain.error.HistoryScanTechnicalException;
import io.sch.historyscan.infrastructure.features.codebase.CodeBaseController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class CurrentCodebasesDTO extends RepresentationModel<CurrentCodebasesDTO> {
    private final List<CurrentCodebaseDTO> codebases;

    @JsonCreator
    public CurrentCodebasesDTO(List<CurrentCodebaseDTO> codebases) {
        this.codebases = codebases;
        try {
            selfLink();
        } catch (NoSuchMethodException e) {
            throw new HistoryScanTechnicalException("No method found in the controller", e);
        }
    }

    private void selfLink() throws NoSuchMethodException {
        add(linkTo(CodeBaseController.class,
                CodeBaseController.class.getMethod("currentCodeBases"))
                .withSelfRel()
                .withTitle(HttpMethod.GET.name()));
    }

    public List<CurrentCodebaseDTO> getCodebases() {
        return codebases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CurrentCodebasesDTO that = (CurrentCodebasesDTO) o;
        return Objects.equals(codebases, that.codebases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), codebases);
    }
}
