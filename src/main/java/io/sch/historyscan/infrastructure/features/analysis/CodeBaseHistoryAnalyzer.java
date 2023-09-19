package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.HistoryAnalysis;
import io.sch.historyscan.infrastructure.features.filesystem.FileSystemManager;
import io.sch.historyscan.infrastructure.hexagonalarchitecture.HexagonalArchitectureAdapter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@HexagonalArchitectureAdapter
public class CodeBaseHistoryAnalyzer implements HistoryAnalysis {

    private final String codebasesFolder;
    private final FileSystemManager fileSystemManager;

    public CodeBaseHistoryAnalyzer(@Value("${io.sch.historyscan.codebases.folder}") String codebasesFolder,
                                   FileSystemManager fileSystemManager) {
        this.codebasesFolder = codebasesFolder;
        this.fileSystemManager = fileSystemManager;
    }

    @Override
    public void of(CodeBaseToAnalyze codeBaseToAnalyze) {
        this.fileSystemManager.findFolder(codebasesFolder, codeBaseToAnalyze.name())
                .map(this::codeBaseHistory);
    }

    private Object codeBaseHistory(File file) {
        try (var codeBase = Git.open(file)) {
            codeBase.log().call().forEach(System.out::println);
        } catch (IOException e) {
            throw new CodeBaseNotOpenedException("Unable to open codebase %s".formatted(file.getName()), e);
        } catch (NoHeadException e) {
            throw new CodeBaseHeadNotFoundException("Unable to find HEAD for codebase %s".formatted(file.getName()), e);
        } catch (GitAPIException e) {
            throw new CodeBaseLogNotFoundException("Unable to find log for codebase %s".formatted(file.getName()), e);
        }
        return null;
    }
}
