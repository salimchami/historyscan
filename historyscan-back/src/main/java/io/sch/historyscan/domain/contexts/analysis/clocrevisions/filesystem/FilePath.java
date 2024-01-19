package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import java.io.File;
import java.util.Objects;

public record FilePath(File currentFile, String rootFolder, String codebaseName, String codebasesPath) {
    public String pathFromCodebaseName(File currentFile) {
        var fullPath = sanitizePath(Objects.requireNonNull(currentFile).getPath());
        var projectPath = sanitizePath(fullPath.substring(0, (codebasesPath).length()));
        var filePath = fullPath.substring(projectPath.length());
        return sanitizePath(filePath);
    }

    public boolean isValid() {
        var filePath = sanitizePath(currentFile.getPath());
        var isGitFolder = filePath.contains("/.git");
        var isCodebasesPath = filePath.equals(sanitizePath(codebasesPath));
        var isCodebasePath = filePath.equals(sanitizePath(codebasesPath) + "/" + codebaseName);
        var containsRootFolder = filePath.contains(sanitizePath(rootFolder));
        return !isGitFolder && !isCodebasesPath && !isCodebasePath && containsRootFolder;
    }

    private String sanitizePath(String path) {
        String sanitizedPAth = path.replace("\\", "/");
        if (sanitizedPAth.endsWith("/")) {
            sanitizedPAth = sanitizedPAth.substring(0, path.length() - 1);
        }
        if (sanitizedPAth.startsWith("/")) {
            sanitizedPAth = sanitizedPAth.substring(1);
        }
        return sanitizedPAth;
    }
}
