package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RootFolder {

    private final String value;
    private final String codebaseName;

    private RootFolder(String value, String codebaseName) {
        this.value = value;
        this.codebaseName = codebaseName;
    }

    public static RootFolder of(String rootFolder, String codeBaseName) {
        var actualValue = formattedValue(rootFolder, codeBaseName);
        return new RootFolder(actualValue, codeBaseName);
    }

    public boolean isIn(File file) {
        var actualValue = actualValue();
        return file.getPath().contains(actualValue) || isInSubFolders(file, actualValue) || codebaseName.equals(file.getName());
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
        return formattedValue(value, codebaseName);
    }

    private static String formattedValue(String value, String codebaseName) {
        if (value.isEmpty() || value.startsWith("/") || value.startsWith("\\")) {
            return codebaseName;
        }
        return value
                .replace("/", "\\")
                .replace("\\\\", "\\");
    }

    public String getValue() {
        return value;
    }
}
