package io.sch.historyscan.domain.contexts.analysis.clocrevisions

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.ActualFileSystemTree
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.RootFolder
import io.sch.historyscan.domain.contexts.analysis.common.*
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory
import io.sch.historyscan.domain.error.HistoryScanFunctionalException
import io.sch.historyscan.domain.hexagonalarchitecture.DDDService

@DDDService
class CodebaseClocRevisionsAnalysis(
    private val historyAnalysis: Analyze<CodeBaseHistory>,
    private val actualFileSystemTree: ActualFileSystemTree
) : Analyze<CodebaseClocRevisions> {
    @Throws(HistoryScanFunctionalException::class)
    override fun from(codeBaseToAnalyze: CodeBaseToAnalyze): CodebaseClocRevisions {
        val history = historyAnalysis.from(codeBaseToAnalyze)
        val rootFolder: RootFolder = RootFolder.of(codeBaseToAnalyze.rootFolder, codeBaseToAnalyze.name)
        val actualFilesTree = actualFileSystemTree.from(rootFolder)
            .then()
            .updateFilesScoreFrom(history.commits)
            .then()
            .updateFoldersScore()
        return CodebaseClocRevisions(
            actualFilesTree,
            actualFilesTree.ignoredRevisions(),
            actualFilesTree.extensions()
        )
    }
}
