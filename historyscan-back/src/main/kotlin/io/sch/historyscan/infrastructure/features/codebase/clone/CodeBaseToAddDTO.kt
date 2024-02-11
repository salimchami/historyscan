package io.sch.historyscan.infrastructure.features.codebase.clone

import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
class CodeBaseToAddDTO(url: String,
                       publicKey: String,
                       name: String,
                       branch: String
) {
    val url: String
    val publicKey: String
    val name: String
    val branch: String

    init {
        this.nbLines = nbLines
        this.addedLines = addedLines
        this.deletedLines = deletedLines
        this.modifiedLines = modifiedLines
        this.codebaseRootFolder = codebaseRootFolder
        this.url = url
        this.publicKey = publicKey
        this.name = name
        this.branch = branch
    }
}
