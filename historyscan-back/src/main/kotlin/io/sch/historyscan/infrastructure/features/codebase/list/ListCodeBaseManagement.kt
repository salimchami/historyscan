package io.sch.historyscan.infrastructure.features.codebase.list

import io.sch.historyscan.domain.contexts.codebase.find.CodeBasesListInventory
import io.sch.historyscan.domain.contexts.codebase.find.CurrentCodeBase
import io.sch.historyscan.infrastructure.common.filesystem.FileSystemManager
import io.sch.historyscan.infrastructure.features.codebase.info.CodeBaseInfoManagement
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File

@Component
class ListCodeBaseManagement(@param:Value("\${io.sch.historyscan.codebases.folder}") private val codebasesFolder: String,
                             private val fileSystemManager: FileSystemManager, private val codeBaseInfoManagement: CodeBaseInfoManagement) : CodeBasesListInventory {
    override fun listAll(): List<CurrentCodeBase> {
        val folders = fileSystemManager.listFoldersFrom(codebasesFolder)
        return folders!!.stream()
                .map { folder: File? -> codeBaseInfoManagement.codeBaseFromFolder(folder) }
                .toList()
    }
}
