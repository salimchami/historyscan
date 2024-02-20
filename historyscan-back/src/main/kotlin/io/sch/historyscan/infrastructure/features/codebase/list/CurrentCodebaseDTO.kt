package io.sch.historyscan.infrastructure.features.codebase.list

import com.fasterxml.jackson.annotation.JsonCreator
import io.sch.historyscan.domain.contexts.analysis.common.EnumAnalysisType
import io.sch.historyscan.domain.error.HistoryScanTechnicalException
import io.sch.historyscan.infrastructure.features.analysis.AnalysisController
import io.sch.historyscan.infrastructure.features.analysis.dto.CodeBaseToAnalyzeDTO
import io.sch.historyscan.infrastructure.features.codebase.CodeBaseController
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpMethod
import java.util.*

class CurrentCodebaseDTO @JsonCreator constructor(val name: String,
                                                  val url: String,
                                                  val currentBranch: String) : RepresentationModel<CurrentCodebaseDTO?>() {
    init {
        addSelfLink()
        addAnalysisLink()
    }

    private fun addAnalysisLink() {
        Arrays.stream(EnumAnalysisType.entries.toTypedArray()).forEach { analysisType: EnumAnalysisType ->
            try {
                add(WebMvcLinkBuilder.linkTo(AnalysisController::class.java, AnalysisController::class.java.getMethod("analyze", CodeBaseToAnalyzeDTO::class.java))
                        .withRel("analyze-" + analysisType.title)
                        .withTitle(HttpMethod.POST.name())
                        .withType(analysisType.title))
            } catch (e: NoSuchMethodException) {
                throw HistoryScanTechnicalException("Error while adding an analysis link", e)
            }
        }
    }

    private fun addSelfLink() {
        add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CodeBaseController::class.java, name).findCodeBase(name))
                .withSelfRel()
                .withTitle(HttpMethod.GET.name()))
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as CurrentCodebaseDTO
        return name == that.name && url == that.url
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), name, url)
    }
}
