package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.sch.historyscan.domain.contexts.analysis.common.EnumIgnoredCodeBaseFiles.isIgnored;

public class CodeBaseFile {
    private final File rootFile;
    private final RootFolder rootFolder;
    private final List<FileInfo> ignoredFiles;

    public CodeBaseFile(File rootFile, RootFolder rootFolder) {
        this.rootFile = rootFile;
        this.rootFolder = rootFolder;
        this.ignoredFiles = new ArrayList<>();
    }

    public boolean hasSameNameAsCodeBase() {
        return rootFile.getName().equals(rootFolder.getCodebaseName());
    }

    public List<FileInfo> children() {
        try (var codeBaseFiles = Files.walk(rootFile.toPath())) {
            return codeBaseFiles.map(Path::toFile)
                    .filter(codeBaseFile -> !codeBaseFile.getPath().contains("/.git"))
                    .map(this::child)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } catch (IOException e) {
            throw new CodebasePathCanNotBeRead("Error while reading codebase files tree", e);
        }
    }

    private Optional<FileInfo> child(File codeBaseFile) {
        var path = pathFromCodebaseName(codeBaseFile);
        if (this.filterIgnoredFiles(codeBaseFile, path) && this.filterFromRootFolder(path)) {
            return Optional.of(new FileInfo(codeBaseFile.getName(), path, codeBaseFile.isFile()));
        }
        return Optional.empty();
    }

    private boolean filterFromRootFolder(String path) {
        return path.contains(rootFolder.getValue());
    }

    private boolean filterIgnoredFiles(File currentFile, String path) {
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
