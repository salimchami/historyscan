package io.sch.historyscan.domain.contexts.analysis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CodeBaseAnalysisTest {

    private HistoryAnalyzer historyAnalyzer;
    private CodeBaseAnalysis sut;

    @BeforeEach
    void setUp() {
        historyAnalyzer = mock(HistoryAnalyzer.class);
        sut = new CodeBaseAnalysis(historyAnalyzer);
    }

    @Test
    void should_load_codebase_history() {
        var codeBaseName = "codebase";
        var codebase = new CodeBase(codeBaseName, EnumAnalysis.COMMITS_SCAN);
        List<CodeBaseFile> commits = List.of();
        when(historyAnalyzer.of(anyString())).thenReturn(Optional.of(new CodeBaseHistory(commits)));

        var history = sut.of(codebase);

        CodeBaseHistory expectedHistory = new CodeBaseHistory(commits);
        assertThat(history).isEqualTo(expectedHistory);
    }
}