package io.sch.historyscan.infrastructure.features.analysis.dto.clocrevisions

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.hateoas.RepresentationModel
import java.util.*

class CodeBaseClocRevisionsFileDTO @JsonCreator constructor(val name: String, val path: String, val parentPath: String, val score: Long) : RepresentationModel<CodeBaseClocRevisionsFileDTO?>() {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as CodeBaseClocRevisionsFileDTO
        return name == that.name && parentPath == that.parentPath && path == that.path && score == that.score
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), name, parentPath, path, score)
    }
}
