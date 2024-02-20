package io.sch.historyscan.infrastructure.features.analysis.dto.history

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.hateoas.RepresentationModel
import java.util.*

class CodeBaseHistoryDTO @JsonCreator constructor(val files: List<CodeBaseHistoryFileDTO>) : RepresentationModel<CodeBaseHistoryDTO?>() {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as CodeBaseHistoryDTO
        return files == that.files
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), files)
    }
}
