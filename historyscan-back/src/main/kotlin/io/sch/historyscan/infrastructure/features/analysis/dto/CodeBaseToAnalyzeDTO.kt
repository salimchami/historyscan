package io.sch.historyscan.infrastructure.features.analysis.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class CodeBaseToAnalyzeDTO @JsonCreator constructor(
    @JsonProperty("name") val name: String,
    @JsonProperty("analysisType") val type: String,
    @JsonProperty("rootFolder") val rootFolder: String
) {
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
