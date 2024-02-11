package io.sch.historyscan.infrastructure.features.filesystem

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.ActualFileSystemTree
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.CodeBaseFile
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemTree
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.RootFolder
import io.sch.historyscan.infrastructure.common.filesystem.FileSystemManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File

@Component
class FileSystemReader(@param:Value("\${io.sch.historyscan.codebases.folder}") private val codebasesFolder: String,
                       private val fileSystemManager: FileSystemManager) : ActualFileSystemTree {
    override fun from(rootFolder: RootFolder): FileSystemTree {
        val tree = FileSystemTree(rootFolder)
        fileSystemManager.findFolder(codebasesFolder, rootFolder.codebaseName)
                .ifPresent { file: File? -> tree.createFrom(CodeBaseFile(file, rootFolder, codebasesFolder)) }
        return tree
    }
}
