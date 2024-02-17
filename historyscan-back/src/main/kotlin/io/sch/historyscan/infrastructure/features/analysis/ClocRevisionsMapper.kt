package io.sch.historyscan.infrastructure.features.analysis

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileInfo
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemNode
import io.sch.historyscan.infrastructure.features.analysis.dto.FileInfoDTO
import io.sch.historyscan.infrastructure.features.analysis.dto.clocrevisions.ClocRevisionsFileNodeDTO
import io.sch.historyscan.infrastructure.features.analysis.dto.clocrevisions.CodeBaseClocRevisionsDTO
import org.springframework.stereotype.Component

@Component
class ClocRevisionsMapper {
    fun domainToWeb(analyzedCodeBaseClocRevisions: CodebaseClocRevisions): CodeBaseClocRevisionsDTO {
        val root = analyzedCodeBaseClocRevisions.actualFsTree.root

        return CodeBaseClocRevisionsDTO(
            domainToWeb(root),
            analyzedCodeBaseClocRevisions.ignoredRevisions.stream()
                .map { fileInfo: FileInfo -> this.fileInfoDomainToWeb(fileInfo) }.toList(),
            analyzedCodeBaseClocRevisions.extensions
        )
    }

    private fun domainToWeb(node: FileSystemNode): ClocRevisionsFileNodeDTO {
        return ClocRevisionsFileNodeDTO(
            node.name, node.path, node.parentPath!!, node.isFile,
            node.currentNbLines, node.revisionScore?.score!!, domainToWebChildren(node.children)
        )
    }

    private fun domainToWebChildren(children: Map<String, FileSystemNode>): List<ClocRevisionsFileNodeDTO> {
        return children.entries.stream().map { stringFileSystemNodeEntry: Map.Entry<String, FileSystemNode> ->
            this.domainToWebEntries(stringFileSystemNodeEntry)
        }.toList()
    }

    private fun domainToWebEntries(stringFileSystemNodeEntry: Map.Entry<String, FileSystemNode>): ClocRevisionsFileNodeDTO {
        return ClocRevisionsFileNodeDTO(
            stringFileSystemNodeEntry.value.name,
            stringFileSystemNodeEntry.value.path,
            stringFileSystemNodeEntry.value.parentPath ?: "",
            stringFileSystemNodeEntry.value.isFile,
            stringFileSystemNodeEntry.value.currentNbLines,
            stringFileSystemNodeEntry.value.revisionScore?.score!!,
            domainToWebChildren(stringFileSystemNodeEntry.value.children)
        )
    }

    private fun fileInfoDomainToWeb(fileInfo: FileInfo): FileInfoDTO {
        return FileInfoDTO(fileInfo.name, fileInfo.path)
    }
}
