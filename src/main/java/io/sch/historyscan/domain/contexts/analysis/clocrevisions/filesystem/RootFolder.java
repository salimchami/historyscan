package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public record RootFolder(String value) {
    public boolean isIn(File file) {
        var actualValue = actualValue();
        return file.getPath().contains(actualValue) || isInSubFolders(file, actualValue);
    }

    private static boolean isInSubFolders(File file, String actualValue) {
        Path path = Paths.get(file.getPath());
        try (var paths = Files.walk(path)) {
            return paths
                    .filter(Files::isDirectory)
                    .anyMatch(p -> p.getFileName().toString().contains(actualValue));
        } catch (IOException e) {
            throw new CodebasePathCanNotBeReadException("Error while reading codebase files tree", e);
        }
    }

    private String actualValue() {
        return value.replace("/", "\\");
    }

    public boolean existsIn(String fullPath) {
        return fullPath.contains(actualValue());
    }
}
