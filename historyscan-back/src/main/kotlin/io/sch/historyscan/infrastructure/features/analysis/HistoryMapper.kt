package io.sch.historyscan.infrastructure.features.analysis

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo
import io.sch.historyscan.infrastructure.features.analysis.dto.history.CodeBaseHistoryCommitFileDTO
import io.sch.historyscan.infrastructure.features.analysis.dto.history.CodeBaseHistoryCommitInfoDTO
import io.sch.historyscan.infrastructure.features.analysis.dto.history.CodeBaseHistoryDTO
import io.sch.historyscan.infrastructure.features.analysis.dto.history.CodeBaseHistoryFileDTO
import org.springframework.stereotype.Component

@Component
class HistoryMapper {
    fun domainToWeb(analyzedCodeBase: CodeBaseHistory): CodeBaseHistoryDTO {
        return CodeBaseHistoryDTO(
                analyzedCodeBase.commits.stream().map { codeBaseCommit: CodeBaseCommit -> this.domainToWeb(codeBaseCommit) }.toList()
        )
    }

    fun domainToWeb(codeBaseCommit: CodeBaseCommit): CodeBaseHistoryFileDTO {
        return CodeBaseHistoryFileDTO(
                this.domainToWeb(codeBaseCommit.info),
                this.domainToWeb(codeBaseCommit.files)
        )
    }

    private fun domainToWeb(files: List<CodeBaseHistoryCommitFile>): List<CodeBaseHistoryCommitFileDTO> {
        return files.stream().map { codeBaseHistoryCommitFile: CodeBaseHistoryCommitFile -> this.domainToWeb(codeBaseHistoryCommitFile) }.toList()
    }

    private fun domainToWeb(codeBaseHistoryCommitFile: CodeBaseHistoryCommitFile): CodeBaseHistoryCommitFileDTO {
        return CodeBaseHistoryCommitFileDTO(
                codeBaseHistoryCommitFile.fileInfo.name,
                codeBaseHistoryCommitFile.fileInfo.path,
                codeBaseHistoryCommitFile.currentNbLines,
                codeBaseHistoryCommitFile.nbAddedLines,
                codeBaseHistoryCommitFile.nbDeletedLines,
                codeBaseHistoryCommitFile.nbModifiedLines)
    }

    private fun domainToWeb(info: CodeBaseHistoryCommitInfo): CodeBaseHistoryCommitInfoDTO {
        return CodeBaseHistoryCommitInfoDTO(info.hash, info.author, info.date, info.shortMessage)
    }
}
