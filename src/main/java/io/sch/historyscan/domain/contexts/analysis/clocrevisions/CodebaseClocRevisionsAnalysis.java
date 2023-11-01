package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.Analysis;
import io.sch.historyscan.domain.contexts.analysis.CodeBase;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

import java.util.stream.Collectors;

public class CodebaseClocRevisionsAnalysis implements Analysis<CodebaseClocRevisions> {

    private final Analysis<CodeBaseHistory> codebaseHistoryAnalysis;

    public CodebaseClocRevisionsAnalysis(Analysis<CodeBaseHistory> codebaseHistoryAnalysis) {
        this.codebaseHistoryAnalysis = codebaseHistoryAnalysis;
    }

    @Override
    public CodebaseClocRevisions of(CodeBase codeBase) throws HistoryScanFunctionalException {
        var history = codebaseHistoryAnalysis.of(codeBase);
        return clocRevisionsFrom(history);
    }

    public CodebaseClocRevisions clocRevisionsFrom(CodeBaseHistory history) {
          var revisions = history.commits().stream()
                .flatMap(codebaseFile -> codebaseFile.files().stream())
                .collect(Collectors.groupingBy(
                        CodeBaseHistoryCommitFile::fileName,
                        Collectors.summingInt(CodeBaseHistoryCommitFile::cloc)
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> new CodebaseFileClocRevisions(entry.getKey(), entry.getValue(), nbLines(entry.getKey(), history)))
                .sorted()
                .toList();
        return CodebaseClocRevisions.of(revisions);
    }

    private int nbLines(String fileName, CodeBaseHistory history) {
        return history.commits().stream()
                .filter(commit -> commit.files().stream().anyMatch(file -> file.fileName().equals(fileName)))
                // take max of ng lines of the object
                .flatMapToInt(commit -> commit.files().stream()
                        .filter(file -> file.fileName().equals(fileName))
                        .mapToInt(CodeBaseHistoryCommitFile::nbLines))
                .max()
                .orElse(0);
    }
}