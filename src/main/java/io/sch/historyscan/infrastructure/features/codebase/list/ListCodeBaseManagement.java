package io.sch.historyscan.infrastructure.features.codebase.list;

import io.sch.historyscan.domain.contexts.codebase.find.CodeBasesListInventory;
import io.sch.historyscan.domain.contexts.codebase.find.CurrentCodeBase;
import io.sch.historyscan.infrastructure.features.codebase.info.CodeBaseInfoManagement;
import io.sch.historyscan.infrastructure.features.filesystem.FileSystemManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListCodeBaseManagement implements CodeBasesListInventory {

    private final String codebasesFolder;
    private final FileSystemManager fileSystemManager;
    private final CodeBaseInfoManagement codeBaseInfoManagement;

    public ListCodeBaseManagement(@Value("${io.sch.historyscan.codebases.folder}") String codebasesFolder,
                                  FileSystemManager fileSystemManager, CodeBaseInfoManagement codeBaseInfoManagement) {
        this.codebasesFolder = codebasesFolder;
        this.fileSystemManager = fileSystemManager;
        this.codeBaseInfoManagement = codeBaseInfoManagement;
    }

    @Override
    public List<CurrentCodeBase> listAll() {
        var folders = this.fileSystemManager.listFoldersFrom(codebasesFolder);
        return folders.stream()
                .map(codeBaseInfoManagement::codeBaseFromFolder)
                .toList();
    }
}
