package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.HistoryAnalyzer;
import io.sch.historyscan.domain.logging.spi.Logger;
import io.sch.historyscan.infrastructure.common.filesystem.FileSystemManager;
import io.sch.historyscan.infrastructure.features.analysis.exceptions.CodeBaseHeadNotFoundException;
import io.sch.historyscan.infrastructure.features.analysis.exceptions.CodeBaseLogNotFoundException;
import io.sch.historyscan.infrastructure.features.analysis.exceptions.CodeBaseNotOpenedException;
import io.sch.historyscan.infrastructure.features.analysis.git.GitWrapper;
import io.sch.historyscan.infrastructure.hexagonalarchitecture.HexagonalArchitectureAdapter;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Component
@HexagonalArchitectureAdapter
public class CodeBaseHistoryAnalyzer implements HistoryAnalyzer {

    private final String codebasesFolder;
    private final FileSystemManager fileSystemManager;
    private final Logger logger;

    public CodeBaseHistoryAnalyzer(@Value("${io.sch.historyscan.codebases.folder}") String codebasesFolder,
                                   FileSystemManager fileSystemManager, Logger logger) {
        this.codebasesFolder = codebasesFolder;
        this.fileSystemManager = fileSystemManager;
        this.logger = logger;
    }

    @Override
    public Optional<CodeBaseHistory> of(String codeBaseName) {
        return this.fileSystemManager.findFolder(codebasesFolder, codeBaseName)
                .map(this::codeBaseHistory);
    }

    private CodeBaseHistory codeBaseHistory(File codebase) {
        try {
            var codebaseCurrentFilesPaths = fileSystemManager.allFilesFrom(codebase, ".git");
            return new GitWrapper(codebase, codebaseCurrentFilesPaths).history();
        } catch (IOException e) {
            logger.error("Unable to open codebase %s".formatted(codebase.getName()), e);
            throw new CodeBaseNotOpenedException("Unable to open codebase %s".formatted(codebase.getName()), e);
        } catch (NoHeadException e) {
            logger.error("Unable to find HEAD for codebase %s".formatted(codebase.getName()), e);
            throw new CodeBaseHeadNotFoundException("Unable to find HEAD for codebase %s".formatted(codebase.getName()), e);
        } catch (GitAPIException e) {
            logger.error("Unable to find log for codebase %s".formatted(codebase.getName()), e);
            throw new CodeBaseLogNotFoundException("Unable to find log for codebase %s".formatted(codebase.getName()), e);
        }
    }
}
