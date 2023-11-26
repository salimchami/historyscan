package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDService;

@DDDService
public class CodebaseClocRevisionsAnalysis implements Analyze<CodebaseClocRevisions> {

    private final Analyze<CodeBaseHistory> historyAnalysis;

    public CodebaseClocRevisionsAnalysis(Analyze<CodeBaseHistory> historyAnalysis) {
        this.historyAnalysis = historyAnalysis;
    }

    @Override
    public CodebaseClocRevisions from(CodeBaseToAnalyze codeBase) throws HistoryScanFunctionalException {
        var history = historyAnalysis.from(codeBase);
        return CodebaseClocRevisions.of(history.commits(), codeBase.getBaseFolder());
    }
}
