package io.sch.historyscan.infrastructure.features.codebase.clone;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sch.historyscan.domain.error.HistoryScanTechnicalException;
import io.sch.historyscan.infrastructure.features.codebase.CodeBaseController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpMethod;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class AddedCodebaseDTO extends RepresentationModel<AddedCodebaseDTO> {

    private final String name;
    private final String url;
    private final String currentBranch;

    @JsonCreator
    public AddedCodebaseDTO(String name,
                            String url,
                            String currentBranch) {
        this.name = name;
        this.url = url;
        this.currentBranch = currentBranch;
        try {
            addSelfLink();
            codeBasesListLink();
        } catch (NoSuchMethodException e) {
            throw new HistoryScanTechnicalException("No method found in the controller", e);
        }
    }

    private void codeBasesListLink() throws NoSuchMethodException {
        add(linkTo(CodeBaseController.class,
                CodeBaseController.class.getMethod("currentCodeBases"))
                .withRel("codebases-list")
                .withTitle(HttpMethod.GET.name()));
    }

    private void addSelfLink() throws NoSuchMethodException {
        add(linkTo(CodeBaseController.class,
                CodeBaseController.class.getMethod("add", CodeBaseToAddDTO.class))
                .withSelfRel()
                .withTitle(HttpMethod.POST.name()));

    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getCurrentBranch() {
        return currentBranch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AddedCodebaseDTO that = (AddedCodebaseDTO) o;
        return Objects.equals(currentBranch, that.currentBranch)
                && Objects.equals(name, that.name)
                && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, url, currentBranch);
    }
}
