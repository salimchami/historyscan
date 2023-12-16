package io.sch.historyscan.infrastructure.features.analysis.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class FileRevisionsLinkDTO extends RepresentationModel<FileRevisionsLinkDTO> {
    private final String from;
    private final Integer fromWeight;
    private final String to;
    private final Integer weight;

    public FileRevisionsLinkDTO(String from, int fromWeight, String to, Integer weight) {
        this.from = from;
        this.fromWeight = fromWeight;
        this.to = to;
        this.weight = weight;
    }

    public String getFrom() {
        return from;
    }

    public Integer getFromWeight() {
        return fromWeight;
    }

    public String getTo() {
        return to;
    }

    public Integer getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FileRevisionsLinkDTO that = (FileRevisionsLinkDTO) o;
        return fromWeight == that.fromWeight && weight == that.weight && Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), from, fromWeight, to, weight);
    }
}
