package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import io.sch.historyscan.domain.contexts.analysis.common.FileInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.sch.historyscan.domain.contexts.analysis.common.EnumIgnoredCodeBaseFiles.isIgnored;

public class CodeBaseFile {
    private final File file;
    private final RootFolder rootFolder;
    private final List<FileInfo> ignoredFiles;

    public CodeBaseFile(File file, RootFolder rootFolder) {
        this.file = file;
        this.rootFolder = rootFolder;
        this.ignoredFiles = new ArrayList<>();
    }

    public boolean hasSameNameAsCodeBase() {
        return file.getName().equals(rootFolder.getCodebaseName());
    }

    public List<FileInfo> children() {
        try (var codeBaseFiles = Files.walk(file.toPath())) {
            return codeBaseFiles
                    .map(Path::toFile)
                    .filter(currentFile -> {
                        var path = pathFromCodebaseName(currentFile);
                        return path.contains(rootFolder.getValue());
                    })
                    .filter(this::filterIgnoredFiles)
                    .map(currentFile -> new FileInfo(currentFile.getName(), pathFromCodebaseName(currentFile), currentFile.isFile()))
                    .toList();
        } catch (IOException e) {
            throw new CodebasePathCanNotBeRead("Error while reading codebase files tree", e);
        }
    }

    private boolean filterIgnoredFiles(File currentFile) {
        var path = pathFromCodebaseName(currentFile);
        var isIgnored = isIgnored(path, currentFile.isFile());
        if (isIgnored && currentFile.isFile()) {
            this.ignoredFiles.add(new FileInfo(currentFile.getName(), path, currentFile.isFile()));
        }
        return !isIgnored;
    }

    private String pathFromCodebaseName(File currentFile) {
        final String path = Objects.requireNonNull(currentFile).getPath().replace("\\", "/");
        return path.contains(rootFolder.getCodebaseName())
                ? path.substring(path.indexOf(rootFolder.getCodebaseName()))
                : path;
    }

    public List<FileInfo> getIgnoredFiles() {
        return ignoredFiles;
    }
}
