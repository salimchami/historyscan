package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.logging.spi.Logger;
import io.sch.historyscan.infrastructure.common.filesystem.FileSystemManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CodeBaseHistoryAnalyzerTest {

    private FileSystemManager fileSystemManager;
    private Logger logger;

    @BeforeEach
    void setUp() {
        fileSystemManager = mock(FileSystemManager.class);
        logger = mock(Logger.class);
        when(fileSystemManager.allFilesFrom(any(), anyString())).thenReturn(
                List.of("/codebases/codebase1/src/main/jaba/io/sch/historyscan/infrastructure/features/analysis/CodeBaseHistoryAnalyzer.java",
                        "/codebases/codebase1/src/main/jaba/io/sch/historyscan/infrastructure/features/analysis/CodeBaseHistory.java")
        );
    }

    @Test
    void name() {
        var history = new CodeBaseHistoryAnalyzer("C:/Users/slim/historyscan/codebases", fileSystemManager, logger)
                .of("codebase1");
        assertThat(history).isPresent();
    }
}