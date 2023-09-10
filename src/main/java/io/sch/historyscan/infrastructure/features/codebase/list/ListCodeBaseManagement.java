package io.sch.historyscan.infrastructure.features.codebase.list;

import io.sch.historyscan.domain.contexts.codebase.find.CodeBasesListInventory;
import io.sch.historyscan.domain.contexts.codebase.find.CurrentCodeBase;
import io.sch.historyscan.infrastructure.features.filesystem.FileSystemManager;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class ListCodeBaseManagement implements CodeBasesListInventory {

    private final String codebasesFolder;
    private final FileSystemManager fileSystemManager;

    public ListCodeBaseManagement(@Value("${io.sch.historyscan.codebases.folder}") String codebasesFolder,
                                  FileSystemManager fileSystemManager) {
        this.codebasesFolder = codebasesFolder;
        this.fileSystemManager = fileSystemManager;
    }

    @Override
    public List<CurrentCodeBase> listAll() {
        var folders = this.fileSystemManager.listFoldersFrom(codebasesFolder);
        return folders.stream().map(this::currentCodeBaseFromGit).toList();
    }

    private CurrentCodeBase currentCodeBaseFromGit(File folder) {
        try (var git = Git.open(folder)) {
            return new CurrentCodeBase(
                    folder.getName(),
                    git.getRepository().getConfig().getString("remote", "origin", "url"),
                    git.getRepository().getBranch(),
                    false);
        } catch (Exception e) {
            return new CurrentCodeBase(folder.getName(), null, null, true);

        }
    }
}
