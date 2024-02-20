package io.sch.historyscan.infrastructure.features.analysis.dto

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.hateoas.RepresentationModel
import java.util.*

class FileInfoDTO @JsonCreator constructor(val name: String, val path: String) : RepresentationModel<FileInfoDTO?>() {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as FileInfoDTO
        return name == that.name && path == that.path
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), name, path)
    }
}
