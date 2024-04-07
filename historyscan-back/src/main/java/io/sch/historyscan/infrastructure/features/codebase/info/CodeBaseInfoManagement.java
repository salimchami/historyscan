package io.sch.historyscan.infrastructure.features.codebase.info;

import io.sch.historyscan.domain.contexts.codebase.find.CodeBaseInfoInventory;
import io.sch.historyscan.domain.contexts.codebase.find.CurrentCodeBase;
import io.sch.historyscan.infrastructure.common.filesystem.FileSystemManager;
import io.sch.historyscan.infrastructure.features.analysis.exceptions.CodeBaseHeadNotFoundException;
import io.sch.historyscan.infrastructure.hexagonalarchitecture.HexagonalArchitectureAdapter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;

@Component
@HexagonalArchitectureAdapter
public class CodeBaseInfoManagement implements CodeBaseInfoInventory {

    private final String codebasesFolder;
    private final FileSystemManager fileSystemManager;

    public CodeBaseInfoManagement(@Value("${io.sch.historyscan.codebases.folder}") String codebasesFolder,
                                  FileSystemManager fileSystemManager) {
        this.codebasesFolder = codebasesFolder;
        this.fileSystemManager = fileSystemManager;
    }

    @Override
    public Optional<CurrentCodeBase> findBy(String name) {
        return this.fileSystemManager.findFolder(codebasesFolder, name)
                .map(this::codeBaseFromFolder);
    }

    public CurrentCodeBase codeBaseFromFolder(File folder) {
        var gitFolder = new GitFolder(folder).toDotGitFolder();
        try (var git = Git.open(gitFolder)) {
            return new CurrentCodeBase(
                    folder.getName(),
                    git.getRepository().getConfig().getString("remote", "origin", "url"),
                    git.getRepository().getBranch(),
                    false);
        } catch (RepositoryNotFoundException e) {
            throw new CodeBaseHeadNotFoundException("The folder %s is not a git repository".formatted(folder.getName()), e);
        } catch (Exception e) {
            return new CurrentCodeBase(folder.getName(), null, null, true);
        }
    }
}
