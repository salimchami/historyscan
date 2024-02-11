package io.sch.historyscan.infrastructure.features.codebase.list

import com.fasterxml.jackson.annotation.JsonCreator
import io.sch.historyscan.domain.error.HistoryScanTechnicalException
import io.sch.historyscan.infrastructure.features.codebase.CodeBaseController
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpMethod
import java.util.*

class CurrentCodebasesDTO @JsonCreator constructor(val codebases: List<CurrentCodebaseDTO>) : RepresentationModel<CurrentCodebasesDTO>() {
    init {
        try {
            selfLink()
        } catch (e: NoSuchMethodException) {
            throw HistoryScanTechnicalException("No method found in the controller", e)
        }
    }

    @Throws(NoSuchMethodException::class)
    private fun selfLink() {
        add(WebMvcLinkBuilder.linkTo(CodeBaseController::class.java,
                CodeBaseController::class.java.getMethod("currentCodeBases"))
                .withSelfRel()
                .withTitle(HttpMethod.GET.name()))
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as CurrentCodebasesDTO
        return codebases == that.codebases
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), codebases)
    }
}
