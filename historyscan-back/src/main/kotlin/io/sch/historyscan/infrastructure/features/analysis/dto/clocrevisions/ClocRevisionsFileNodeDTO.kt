package io.sch.historyscan.infrastructure.features.analysis.dto.clocrevisions

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel
import java.util.*

class ClocRevisionsFileNodeDTO @JsonCreator constructor(val name: String, val path: String, val parentPath: String,
                                                        @get:JsonProperty("isFile") @param:JsonProperty("isFile") val isFile: Boolean, val currentNbLines: Long, val score: Long,
                                                        val children: List<ClocRevisionsFileNodeDTO>) : RepresentationModel<ClocRevisionsFileNodeDTO?>() {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as ClocRevisionsFileNodeDTO
        return isFile == that.isFile && currentNbLines == that.currentNbLines && score == that.score && name == that.name && path == that.path && parentPath == that.parentPath && children == that.children
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), name, path, parentPath, isFile, currentNbLines, score, children)
    }
}
