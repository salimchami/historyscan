package io.sch.historyscan.infrastructure.features.analysis

import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory
import io.sch.historyscan.domain.contexts.analysis.history.HistoryAnalyzer
import io.sch.historyscan.domain.logging.spi.Logger
import io.sch.historyscan.infrastructure.common.filesystem.FileSystemManager
import io.sch.historyscan.infrastructure.features.analysis.exceptions.CodeBaseHeadNotFoundException
import io.sch.historyscan.infrastructure.features.analysis.exceptions.CodeBaseLogNotFoundException
import io.sch.historyscan.infrastructure.features.analysis.exceptions.CodeBaseNotOpenedException
import io.sch.historyscan.infrastructure.features.analysis.git.GitWrapper
import io.sch.historyscan.infrastructure.hexagonalarchitecture.HexagonalArchitectureAdapter
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.api.errors.NoHeadException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.io.IOException

@Component
@HexagonalArchitectureAdapter
class CodeBaseHistoryAnalyzer(
    @param:Value("\${io.sch.historyscan.codebases.folder}") private val codebasesFolder: String,
    private val fileSystemManager: FileSystemManager, private val logger: Logger
) : HistoryAnalyzer {
    override fun of(codeBaseName: String): CodeBaseHistory {
        return fileSystemManager.findFolder(codebasesFolder, codeBaseName)
            .map { codebase: File -> this.codeBaseHistory(codebase) }
    }

    private fun codeBaseHistory(codebase: File?): CodeBaseHistory {
        try {
            val codebaseCurrentFilesPaths = fileSystemManager.allFilesFrom(codebase, ".git")
            return GitWrapper(codebase, codebaseCurrentFilesPaths).history()
        } catch (e: IOException) {
            logger.error("Unable to open codebase %s".format(codebase!!.name), e)
            throw CodeBaseNotOpenedException("Unable to open codebase %s".format(codebase.name), e)
        } catch (e: NoHeadException) {
            logger.error("Unable to find HEAD for codebase %s".format(codebase!!.name), e)
            throw CodeBaseHeadNotFoundException("Unable to find HEAD for codebase %s".format(codebase.name), e)
        } catch (e: GitAPIException) {
            logger.error("Unable to find log for codebase %s".format(codebase!!.name), e)
            throw CodeBaseLogNotFoundException("Unable to find log for codebase %s".format(codebase.name), e)
        }
    }
}
