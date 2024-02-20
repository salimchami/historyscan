package io.sch.historyscan.infrastructure.features.codebase.clone

import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
class CodeBaseToAddDTO(
    val url: String,
    val publicKey: String,
    val name: String,
    val branch: String
) {}
