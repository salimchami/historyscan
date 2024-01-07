package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.ActualFileSystemTree;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.RootFolder;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.llm.AnalyzeCodeBaseByLlm;
import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDService;

@DDDService
public class CodebaseClocRevisionsAnalysis implements Analyze<CodebaseClocRevisions> {

    private final Analyze<CodeBaseHistory> historyAnalysis;
    private final ActualFileSystemTree actualFileSystemTree;
    public final AnalyzeCodeBaseByLlm analyzeCodeBaseByLlm;

    public CodebaseClocRevisionsAnalysis(Analyze<CodeBaseHistory> historyAnalysis, ActualFileSystemTree actualFileSystemTree, AnalyzeCodeBaseByLlm analyzeCodeBaseByLlm) {
        this.historyAnalysis = historyAnalysis;
        this.actualFileSystemTree = actualFileSystemTree;
        this.analyzeCodeBaseByLlm = analyzeCodeBaseByLlm;
    }

    @Override
    public CodebaseClocRevisions from(CodeBaseToAnalyze codeBase) throws HistoryScanFunctionalException {
        var history = historyAnalysis.from(codeBase);
        final RootFolder rootFolder = RootFolder.of(codeBase.getRootFolder(), codeBase.getName());
        var actualFilesTree = actualFileSystemTree.
                from(rootFolder)
                .then()
                .updateFilesScoreFrom(history.commits())
                .then()
                .updateFoldersScore();
        final String llmAnalysis = analyzeCodeBaseByLlm.analyzeCodebase(actualFilesTree.getRoot());
        return new CodebaseClocRevisions(
                actualFilesTree,
                llmAnalysis,
                actualFilesTree.ignoredRevisions(),
                actualFilesTree.extensions());
    }
}
