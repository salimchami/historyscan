package io.sch.historyscan.infrastructure.features.filesystem;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.ActualFileSystemTree;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.CodeBaseFile;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemTree;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.RootFolder;
import io.sch.historyscan.infrastructure.common.filesystem.FileSystemManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileSystemReader implements ActualFileSystemTree {
    private final String codebasesFolder;
    private final FileSystemManager fileSystemManager;

    public FileSystemReader(@Value("${io.sch.historyscan.codebases.folder}") String codebasesFolder,
                            FileSystemManager fileSystemManager) {
        this.codebasesFolder = codebasesFolder;
        this.fileSystemManager = fileSystemManager;
    }

    @Override
    public FileSystemTree from(RootFolder rootFolder) {
        FileSystemTree tree = new FileSystemTree(rootFolder);
        this.fileSystemManager.findFolder(codebasesFolder, rootFolder.getCodebaseName())
                .ifPresent(file -> tree.createFrom(new CodeBaseFile(file,  rootFolder)));
        return tree;
    }
}
