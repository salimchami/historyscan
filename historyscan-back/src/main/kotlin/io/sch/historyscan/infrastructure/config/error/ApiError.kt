package io.sch.historyscan.infrastructure.config.error

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

class ApiError @JsonCreator constructor(val message: String?, val url: String) : RepresentationModel<ApiError?>(), Serializable {
    val timeStamp: LocalDateTime = LocalDateTime.now()

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val apiError = o as ApiError
        return timeStamp == apiError.timeStamp && message == apiError.message && url == apiError.url
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), timeStamp, message, url)
    }
}
