package io.sch.historyscan.infrastructure.features.analysis.dto.network

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

class NetworkNodeDTO @JsonCreator constructor(val name: String, val path: String, val parentPath: String, val currentNbLines: Long, val score: Long, val links: List<NetworkLinkDTO>) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as NetworkNodeDTO
        return currentNbLines == that.currentNbLines && score == that.score && name == that.name && path == that.path && parentPath == that.parentPath && links == that.links
    }

    override fun hashCode(): Int {
        return Objects.hash(name, path, parentPath, currentNbLines, score, links)
    }
}
