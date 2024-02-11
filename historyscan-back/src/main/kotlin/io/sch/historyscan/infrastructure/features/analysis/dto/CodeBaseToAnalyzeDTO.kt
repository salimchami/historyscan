package io.sch.historyscan.infrastructure.features.analysis.dto

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

class CodeBaseToAnalyzeDTO @JsonCreator constructor(val name: String,
                                                    val type: String, val rootFolder: String) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as CodeBaseToAnalyzeDTO
        return name == that.name && type == that.type && rootFolder == that.rootFolder
    }

    override fun hashCode(): Int {
        return Objects.hash(name, type, rootFolder)
    }
}
