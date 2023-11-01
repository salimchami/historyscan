package io.sch.historyscan.domain.contexts.analysis;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisionsAnalysis;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseFileClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.common.Analysis;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.common.EnumAnalysisType;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo;
import io.sch.historyscan.domain.contexts.analysis.history.CodebaseHistoryAnalysis;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CodebaseClocRevisionsAnalysisTest {
    private CodebaseClocRevisionsAnalysis sut;
    private Analysis<CodeBaseHistory> historyAnalysis;
    private LocalDateTime date;

    @BeforeEach
    void setUp() {
        historyAnalysis = mock(CodebaseHistoryAnalysis.class);
        sut = new CodebaseClocRevisionsAnalysis(historyAnalysis);
        date = LocalDateTime.now();
    }

    @Test
    void should_calculate_codebase_commits_files_number_of_revisions() throws HistoryScanFunctionalException {
        CodeBaseToAnalyze codeBaseToAnalyze = new CodeBaseToAnalyze("the-code-base", EnumAnalysisType.CLOC_REVISIONS);
        final String fileName1 = "file-1.java";
        final String fileName2 = "file-2.java";
        when(historyAnalysis.of(codeBaseToAnalyze)).thenReturn(new CodeBaseHistory(List.of(
                        new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-1", "author-1", date, "message 1"), List.of(
                                new CodeBaseHistoryCommitFile(fileName1, 20, 1, 2, 5),
                                new CodeBaseHistoryCommitFile(fileName2, 1500, 15, 30, 10)
                        )),
                        new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-2", "author-1", date, "message 2"), List.of(
                                new CodeBaseHistoryCommitFile(fileName1, 20, 12, 0, 2),
                                new CodeBaseHistoryCommitFile(fileName2, 1500,150, 100, 45)
                        )),
                        new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-3", "author-1", date, "message 3"), List.of(
                                new CodeBaseHistoryCommitFile(fileName1, 20, 12, 0, 10)
                        ))
                ))
        );

        var revisions = sut.of(codeBaseToAnalyze);
        assertThat(revisions).isEqualTo(new CodebaseClocRevisions(List.of(
                new CodebaseFileClocRevisions(fileName2, 350, 1500),
                new CodebaseFileClocRevisions(fileName1, 44, 20)
        ), List.of(), List.of("java")));
    }
}