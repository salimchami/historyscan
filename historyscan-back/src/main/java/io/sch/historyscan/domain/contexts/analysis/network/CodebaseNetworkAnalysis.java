package io.sch.historyscan.domain.contexts.analysis.network;

import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDService;

@DDDService
public class CodebaseNetworkAnalysis implements Analyze<CodebaseRevisionsNetwork> {

    private final Analyze<CodeBaseHistory> historyAnalysis;

    public CodebaseNetworkAnalysis(Analyze<CodeBaseHistory> historyAnalysis) {
        this.historyAnalysis = historyAnalysis;
    }


    @Override
    public CodebaseRevisionsNetwork from(CodeBaseToAnalyze codeBase) throws HistoryScanFunctionalException {
        var history = historyAnalysis.from(codeBase);
        throw new HistoryScanFunctionalException("not yet implemented");
    }
}
