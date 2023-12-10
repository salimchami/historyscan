package io.sch.historyscan.infrastructure.features.codebase.info;

import java.io.File;

public record GitFolder(File codebaseRootFolder) {
    public File toDotGitFolder() {
        if (codebaseRootFolder.isDirectory() && !codebaseRootFolder.getName().equals(".git")) {
            return new File(codebaseRootFolder, ".git");
        } else if (codebaseRootFolder.isFile()) {
            return new File(codebaseRootFolder.getParent(), ".git");
        }
        throw new CodeBaseGitFolderNotFoundException("The codebase root folder is not a file or a directory");
    }
}
