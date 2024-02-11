package io.sch.historyscan.infrastructure.features.analysis.dto.network

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

class NetworkLinkDTO @JsonCreator constructor(val path: String, val weight: Long) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as NetworkLinkDTO
        return weight == that.weight && path == that.path
    }

    override fun hashCode(): Int {
        return Objects.hash(path, weight)
    }
}
