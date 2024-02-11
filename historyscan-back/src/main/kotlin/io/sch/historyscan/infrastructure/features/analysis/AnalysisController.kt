package io.sch.historyscan.infrastructure.features.analysis

import io.sch.historyscan.domain.error.HistoryScanFunctionalException
import io.sch.historyscan.infrastructure.common.WebConstants
import io.sch.historyscan.infrastructure.features.analysis.dto.CodeBaseToAnalyzeDTO
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [WebConstants.ENDPOINT_ROOT])
class AnalysisController(private val analysisApplication: AnalysisApplication) {
    @PostMapping(path = ["/analyze"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(HistoryScanFunctionalException::class)
    fun analyze(@RequestBody codeBase: CodeBaseToAnalyzeDTO): ResponseEntity<Any> {
        val analysis = analysisApplication.analyze(codeBase)
        return ResponseEntity.ok(analysis)
    }
}
