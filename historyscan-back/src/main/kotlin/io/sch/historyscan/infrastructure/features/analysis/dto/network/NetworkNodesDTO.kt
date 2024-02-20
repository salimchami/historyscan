package io.sch.historyscan.infrastructure.features.analysis.dto.network

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.hateoas.RepresentationModel
import java.util.*

class NetworkNodesDTO @JsonCreator constructor(val nodes: List<NetworkNodeDTO>) : RepresentationModel<NetworkNodesDTO?>() {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as NetworkNodesDTO
        return nodes == that.nodes
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), nodes)
    }
}
