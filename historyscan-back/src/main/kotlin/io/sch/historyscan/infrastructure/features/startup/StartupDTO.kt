package io.sch.historyscan.infrastructure.features.startup

import com.fasterxml.jackson.annotation.JsonCreator
import io.sch.historyscan.domain.error.HistoryScanTechnicalException
import io.sch.historyscan.infrastructure.features.codebase.CodeBaseController
import io.sch.historyscan.infrastructure.features.codebase.clone.CodeBaseToAddDTO
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpMethod
import java.util.*

class StartupDTO @JsonCreator constructor() : RepresentationModel<StartupDTO?>() {
    val message: String = "Application is up! Please use the links below to navigate to the API endpoints."

    init {
        try {
            addSelfLink()
            addCodebase()
            currentCodebases()
        } catch (e: NoSuchMethodException) {
            throw HistoryScanTechnicalException("No method found in the controller", e)
        }
    }

    @Throws(NoSuchMethodException::class)
    private fun currentCodebases() {
        add(WebMvcLinkBuilder.linkTo(CodeBaseController::class.java,
                CodeBaseController::class.java.getMethod("currentCodeBases"))
                .withRel("list-codebases")
                .withTitle(HttpMethod.GET.name())
        )
    }

    @Throws(NoSuchMethodException::class)
    private fun addCodebase() {
        add(WebMvcLinkBuilder.linkTo(CodeBaseController::class.java,
                CodeBaseController::class.java.getMethod("add", CodeBaseToAddDTO::class.java))
                .withRel("add-codebase")
                .withTitle(HttpMethod.POST.name())
        )
    }

    @Throws(NoSuchMethodException::class)
    private fun addSelfLink() {
        add(WebMvcLinkBuilder.linkTo(StartupController::class.java,
                StartupController::class.java.getMethod("startup"))
                .withSelfRel()
                .withName("startup")
                .withTitle(HttpMethod.GET.name())
        )
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as StartupDTO
        return message == that.message
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), message)
    }
}
