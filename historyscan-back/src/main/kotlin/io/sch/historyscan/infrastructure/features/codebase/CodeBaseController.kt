package io.sch.historyscan.infrastructure.features.codebase

import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseAlreadyExistsException
import io.sch.historyscan.infrastructure.common.WebConstants
import io.sch.historyscan.infrastructure.features.codebase.clone.AddedCodebaseDTO
import io.sch.historyscan.infrastructure.features.codebase.clone.CodeBaseToAddDTO
import io.sch.historyscan.infrastructure.features.codebase.info.CodebaseDTO
import io.sch.historyscan.infrastructure.features.codebase.list.CurrentCodebasesDTO
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = [WebConstants.ENDPOINT_ROOT + "/codebases"])
class CodeBaseController(private val codebaseApplication: CodeBaseApplication) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(CodeBaseAlreadyExistsException::class)
    fun add(@RequestBody codeBaseToAddDTO: CodeBaseToAddDTO): ResponseEntity<AddedCodebaseDTO> {
        val addedCodeBase = codebaseApplication.clone(codeBaseToAddDTO)
        return ResponseEntity(addedCodeBase, HttpStatus.CREATED)
    }

    @GetMapping(path = ["/{codebaseName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findCodeBase(@PathVariable("codebaseName") codebaseName: String): ResponseEntity<CodebaseDTO> {
        val codebase = codebaseApplication.findCodeBase(codebaseName)
        return codebase?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping
    fun currentCodeBases(): ResponseEntity<CurrentCodebasesDTO> {
        return ResponseEntity.ok(codebaseApplication.currentCodeBases())
    }
}
