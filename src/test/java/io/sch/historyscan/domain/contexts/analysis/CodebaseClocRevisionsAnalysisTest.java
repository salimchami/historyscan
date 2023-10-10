package io.sch.historyscan.domain.contexts.analysis;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CodebaseClocRevisionsAnalysisTest {
    private CodebaseClocRevisionsAnalysis codebaseClocRevisionsAnalysis;
    private Analysis<CodeBaseHistory> historyAnalysis;
    private LocalDateTime date;

    @BeforeEach
    void setUp() {
        historyAnalysis = (Analysis<CodeBaseHistory>) mock(Analysis.class);
        codebaseClocRevisionsAnalysis = new CodebaseClocRevisionsAnalysis(historyAnalysis);
        date = LocalDateTime.now();
    }

    @Test
    void should_calculate_codebase_commits_files_number_of_revisions() throws HistoryScanFunctionalException {
        CodeBase codeBase = new CodeBase("the-code-base", EnumAnalysis.CLOC_REVISIONS);
        final String fileName1 = "file-1.java";
        final String fileName2 = "file-2.java";
        when(historyAnalysis.of(codeBase)).thenReturn(new CodeBaseHistory(List.of(
                        new CodeBaseFile(new CodeBaseHistoryCommitInfo("commit-1", "author-1", date, "message 1"), List.of(
                                new CodeBaseHistoryCommitFile(fileName1, 1, 2, 5),
                                new CodeBaseHistoryCommitFile(fileName2, 15, 30, 10)
                        )),
                        new CodeBaseFile(new CodeBaseHistoryCommitInfo("commit-2", "author-1", date, "message 2"), List.of(
                                new CodeBaseHistoryCommitFile(fileName1, 12, 0, 2),
                                new CodeBaseHistoryCommitFile(fileName2, 150, 100, 45)
                        )),
                        new CodeBaseFile(new CodeBaseHistoryCommitInfo("commit-3", "author-1", date, "message 3"), List.of(
                                new CodeBaseHistoryCommitFile(fileName1, 12, 0, 10)
                        ))
                ))
        );

        var analysis = codebaseClocRevisionsAnalysis.of(codeBase);
        assertThat(analysis).isEqualTo(CodebaseClocRevisions.of(List.of(
                new CodebaseFileClocRevisions(fileName1, 44),
                new CodebaseFileClocRevisions(fileName2, 350)
        )));
    }
}