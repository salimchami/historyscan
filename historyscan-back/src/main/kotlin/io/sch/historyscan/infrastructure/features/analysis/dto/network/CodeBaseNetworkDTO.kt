package io.sch.historyscan.infrastructure.features.analysis.dto.network

import com.fasterxml.jackson.annotation.JsonCreator
import io.sch.historyscan.infrastructure.features.analysis.dto.FileInfoDTO
import org.springframework.hateoas.RepresentationModel

class CodeBaseNetworkDTO @JsonCreator constructor(val network: NetworkNodesDTO, val ignoredRevisions: List<FileInfoDTO>, val extensions: List<String>) : RepresentationModel<CodeBaseNetworkDTO?>()
