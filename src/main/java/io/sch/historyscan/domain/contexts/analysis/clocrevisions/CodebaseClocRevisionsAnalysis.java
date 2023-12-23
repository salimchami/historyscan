package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.ActualFileSystemTree;
import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDService;

@DDDService
public class CodebaseClocRevisionsAnalysis implements Analyze<CodebaseClocRevisions> {

    private final Analyze<CodeBaseHistory> historyAnalysis;
    private final ActualFileSystemTree actualFileSystemTree;

    public CodebaseClocRevisionsAnalysis(Analyze<CodeBaseHistory> historyAnalysis, ActualFileSystemTree actualFileSystemTree) {
        this.historyAnalysis = historyAnalysis;
        this.actualFileSystemTree = actualFileSystemTree;
    }

    @Override
    public CodebaseClocRevisions from(CodeBaseToAnalyze codeBase) throws HistoryScanFunctionalException {
        var history = historyAnalysis.from(codeBase);
        var actualFilesTree = actualFileSystemTree.
                from(codeBase.getRootFolder(), codeBase.getName())
                .then()
                .updateFilesScoreFrom(history.commits())
                .then()
                .updateFoldersScore();
        return new CodebaseClocRevisions(
                actualFilesTree,
                actualFilesTree.ignoredRevisions(),
                actualFilesTree.extensions());
    }
}
