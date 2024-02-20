package io.sch.historyscan.infrastructure.features.analysis.dto.clocrevisions

import com.fasterxml.jackson.annotation.JsonCreator
import io.sch.historyscan.infrastructure.features.analysis.dto.FileInfoDTO
import org.springframework.hateoas.RepresentationModel

class CodeBaseClocRevisionsDTO @JsonCreator constructor(val node: ClocRevisionsFileNodeDTO, val ignoredRevisions: List<FileInfoDTO>,
                                                        val extensions: List<String>) : RepresentationModel<CodeBaseClocRevisionsDTO?>()
