package io.sch.historyscan.domain.contexts.analysis;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisionsAnalysis;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseFileClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.common.Analyze;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.common.EnumAnalysisType;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodebaseHistoryAnalysis;
import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.sch.historyscan.fake.CodeBaseCommitFake.defaultHistory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CodebaseClocRevisionsAnalysisTest {
    private CodebaseClocRevisionsAnalysis sut;
    private Analyze<CodeBaseHistory> historyAnalysis;

    @BeforeEach
    void setUp() {
        historyAnalysis = mock(CodebaseHistoryAnalysis.class);
        sut = new CodebaseClocRevisionsAnalysis(historyAnalysis);
    }

    @Test
    void should_calculate_codebase_commits_files_number_of_revisions() throws HistoryScanFunctionalException {
        var codeBaseToAnalyze = new CodeBaseToAnalyze("the-code-base", EnumAnalysisType.CLOC_REVISIONS);

        when(historyAnalysis.from(codeBaseToAnalyze)).thenReturn(defaultHistory());

        var revisions = sut.from(codeBaseToAnalyze);

        var expectedRevisions = List.of(
                new CodebaseFileClocRevisions("file-2.java", 350, 1500),
                new CodebaseFileClocRevisions("file-1.java", 44, 20)
        );
        var expectedIgnoredRevisions = List.<CodebaseFileClocRevisions>of();
        var expectedExtensions = List.of("java");
        var expectedClocRevisions = new CodebaseClocRevisions(expectedRevisions, expectedIgnoredRevisions, expectedExtensions);
        assertThat(revisions).isEqualTo(expectedClocRevisions);
    }
}