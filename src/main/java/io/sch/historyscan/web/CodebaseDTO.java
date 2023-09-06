package io.sch.historyscan.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class CodebaseDTO extends RepresentationModel<CodebaseDTO> {
    private final String name;
    private final String url;

    @JsonCreator
    public CodebaseDTO(String name, String url) {
        this.name = name;
        this.url = url;
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
