package io.sch.historyscan.infrastructure.features.codebase.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sch.historyscan.infrastructure.features.codebase.CodeBaseController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpMethod;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CurrentCodebaseDTO extends RepresentationModel<CurrentCodebaseDTO> {
    private final String name;
    private final String url;
    private final String currentBranch;

    @JsonCreator
    public CurrentCodebaseDTO(String name, String url,
                              @JsonProperty("current-branch") String currentBranch) {
        this.name = name;
        this.url = url;
        this.currentBranch = currentBranch;
        addSelfLink();
    }

    private void addSelfLink() {
        add(linkTo(methodOn(CodeBaseController.class, name).findCodeBase(name))
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
        CurrentCodebaseDTO that = (CurrentCodebaseDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, url);
    }
}
