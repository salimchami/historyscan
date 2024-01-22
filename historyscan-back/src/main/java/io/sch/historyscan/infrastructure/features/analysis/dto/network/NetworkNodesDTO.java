package io.sch.historyscan.infrastructure.features.analysis.dto.network;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class NetworkNodesDTO extends RepresentationModel<NetworkNodesDTO> {
    private final List<NetworkNodeDTO> nodes;

    @JsonCreator
    public NetworkNodesDTO(List<NetworkNodeDTO> nodes) {
        this.nodes = nodes;
    }

    public List<NetworkNodeDTO> getNodes() {
        return nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NetworkNodesDTO that = (NetworkNodesDTO) o;
        return Objects.equals(nodes, that.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nodes);
    }
}
