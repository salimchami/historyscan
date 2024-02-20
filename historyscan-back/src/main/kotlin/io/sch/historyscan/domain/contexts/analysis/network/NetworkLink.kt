package io.sch.historyscan.domain.contexts.analysis.network

import java.util.*

class NetworkLink(val path: String, var weight: Long) : Comparable<NetworkLink> {
    fun incrementWeight() {
        this.weight += 1
    }

    override fun compareTo(other: NetworkLink): Int {
        Objects.requireNonNull(other, "NetworkLink cannot be compared to null")
        return Comparator
            .comparing({ obj: NetworkLink -> obj.weight }, Collections.reverseOrder())
            .thenComparing { obj: NetworkLink -> obj.path }
            .compare(this, other)
    }
}
