package io.sch.historyscan.infrastructure.features.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class FileRevisionsLinkDTO extends RepresentationModel<FileRevisionsLinkDTO> {
    private final String name;
    private final int weight;

    @JsonCreator
    public FileRevisionsLinkDTO(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FileRevisionsLinkDTO that = (FileRevisionsLinkDTO) o;
        return weight == that.weight && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, weight);
    }
}
