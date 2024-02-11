package io.sch.historyscan.infrastructure.features.analysis.dto.history

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.hateoas.RepresentationModel
import java.util.*

class CodeBaseHistoryCommitFileDTO @JsonCreator constructor(val fileName: String,
                                                            val filePath: String,
                                                            val nbActualLines: Int,
                                                            val nbAddedLines: Int,
                                                            val nbDeletedLines: Int,
                                                            val nbModifiedLines: Int) : RepresentationModel<CodeBaseHistoryCommitFileDTO?>() {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as CodeBaseHistoryCommitFileDTO
        return nbActualLines == that.nbActualLines && nbAddedLines == that.nbAddedLines && nbDeletedLines == that.nbDeletedLines && nbModifiedLines == that.nbModifiedLines && fileName == that.fileName && filePath == that.filePath
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(),
                nbActualLines,
                nbAddedLines,
                nbDeletedLines,
                nbModifiedLines,
                fileName,
                filePath)
    }
}
