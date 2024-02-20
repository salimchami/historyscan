package io.sch.historyscan.infrastructure.features.analysis.dto.history

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.hateoas.RepresentationModel
import java.time.LocalDateTime
import java.util.*

class CodeBaseHistoryCommitInfoDTO @JsonCreator constructor(val hash: String, val author: String, val date: LocalDateTime, val shortMessage: String) : RepresentationModel<CodeBaseHistoryCommitInfoDTO?>() {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as CodeBaseHistoryCommitInfoDTO
        return hash == that.hash && author == that.author && date == that.date && shortMessage == that.shortMessage
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), hash, author, date, shortMessage)
    }
}
