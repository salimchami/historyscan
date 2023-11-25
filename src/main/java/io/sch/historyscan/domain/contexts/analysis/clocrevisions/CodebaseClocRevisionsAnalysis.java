package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

import java.util.stream.Collectors;

public class CodebaseClocRevisionsAnalysis implements Analyze<CodebaseClocRevisions> {

    private final Analyze<CodeBaseHistory> codebaseHistoryAnalysis;

    public CodebaseClocRevisionsAnalysis(Analyze<CodeBaseHistory> codebaseHistoryAnalysis) {
        this.codebaseHistoryAnalysis = codebaseHistoryAnalysis;
    }

    @Override
    public CodebaseClocRevisions from(CodeBaseToAnalyze codeBaseToAnalyze) throws HistoryScanFunctionalException {
        var history = codebaseHistoryAnalysis.from(codeBaseToAnalyze);
        return clocRevisionsFrom(history);
    }


    public CodebaseClocRevisions clocRevisionsFrom(CodeBaseHistory history) {
        var commitsByFile = history.commits().stream()
                .flatMap(codebaseFile -> codebaseFile.files().stream())
                .collect(Collectors.groupingBy(
                        CodeBaseHistoryCommitFile::name,
                        Collectors.summingInt(CodeBaseHistoryCommitFile::cloc)
                ));
        var revisions = new CommitsByFile(commitsByFile)
                .toRevisionsFrom(history);
        return CodebaseClocRevisions.of(revisions);
    }
}
