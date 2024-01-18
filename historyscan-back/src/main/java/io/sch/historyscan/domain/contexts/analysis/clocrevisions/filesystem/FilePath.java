package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import java.io.File;
import java.util.Objects;

public record FilePath(File currentFile, String rootFolder, String codebaseName, String codebasesPath) {
    public String pathFromCodebaseName(File currentFile) {
        var fullPath = Objects.requireNonNull(currentFile).getPath().replace("\\", "/");
        var projectPath = fullPath.substring(0, (codebasesPath + "/" + codebaseName).length());
        var filePath = fullPath.substring(projectPath.length());
        return sanitizePath(filePath);
    }

    public boolean isValid() {
        var filePath = currentFile.getPath().replace("\\", "/");
        return !filePath.contains("/.git")
               && !filePath.equals(codebasesPath.replace("\\", "/"))
               && filePath.contains(rootFolder);
    }

    private String sanitizePath(String path) {
        String sanitizedPAth = "";
        if (path.endsWith("/")) {
            sanitizedPAth = path.substring(0, path.length() - 1);
        }
        if (path.startsWith("/")) {
            sanitizedPAth = path.substring(1);
        }
        return sanitizedPAth;
    }
}
