package io.sch.historyscan.infrastructure.features.codebase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class AddedCodebaseDTO extends RepresentationModel<AddedCodebaseDTO> {

    private final String name;
    private final String url;
    private final String currentBranch;

    @JsonCreator
    public AddedCodebaseDTO(String name, String url,
                            @JsonProperty("current-branch") String currentBranch) {
        this.name = name;
        this.url = url;
        this.currentBranch = currentBranch;
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
