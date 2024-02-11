package io.sch.historyscan.infrastructure.features.codebase.clone

import com.fasterxml.jackson.annotation.JsonCreator
import io.sch.historyscan.domain.error.HistoryScanTechnicalException
import io.sch.historyscan.infrastructure.features.codebase.CodeBaseController
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpMethod
import java.util.*

class AddedCodebaseDTO @JsonCreator constructor(val name: String,
                                                val url: String,
                                                val currentBranch: String) : RepresentationModel<AddedCodebaseDTO?>() {
    init {
        try {
            addSelfLink()
            codeBasesListLink()
        } catch (e: NoSuchMethodException) {
            throw HistoryScanTechnicalException("No method found in the controller", e)
        }
    }

    @Throws(NoSuchMethodException::class)
    private fun codeBasesListLink() {
        add(WebMvcLinkBuilder.linkTo(CodeBaseController::class.java,
                CodeBaseController::class.java.getMethod("currentCodeBases"))
                .withRel("codebases-list")
                .withTitle(HttpMethod.GET.name()))
    }

    @Throws(NoSuchMethodException::class)
    private fun addSelfLink() {
        add(WebMvcLinkBuilder.linkTo(CodeBaseController::class.java,
                CodeBaseController::class.java.getMethod("add", CodeBaseToAddDTO::class.java))
                .withSelfRel()
                .withTitle(HttpMethod.POST.name()))
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as AddedCodebaseDTO
        return currentBranch == that.currentBranch && name == that.name && url == that.url
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), name, url, currentBranch)
    }
}
