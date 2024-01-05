package io.sch.historyscan.infrastructure.features.analysis.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class ClocRevisionsFileTreeDTO extends RepresentationModel<ClocRevisionsFileTreeDTO> {

    private final ClocRevisionsFileNodeDTO node;

    @JsonCreator
    public ClocRevisionsFileTreeDTO(ClocRevisionsFileNodeDTO node) {
        this.node = node;
    }

    public ClocRevisionsFileNodeDTO getNode() {
        return node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClocRevisionsFileTreeDTO that = (ClocRevisionsFileTreeDTO) o;
        return Objects.equals(node, that.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), node);
    }
}
