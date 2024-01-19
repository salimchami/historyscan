package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.sch.historyscan.domain.contexts.analysis.common.EnumIgnoredCodeBaseFiles.isIgnored;

public class CodeBaseFile {
    private final File rootFile;
    private final RootFolder rootFolder;
    private final List<FileInfo> ignoredFiles;
    private final String codebasesPath;

    public CodeBaseFile(File rootFile, RootFolder rootFolder, String codebasesPath) {
        this.rootFile = rootFile;
        this.rootFolder = rootFolder;
        this.codebasesPath = codebasesPath;
        this.ignoredFiles = new ArrayList<>();
    }

    public boolean hasSameNameAsCodeBase() {
        return rootFile.getName().equals(rootFolder.getCodebaseName());
    }

    public List<FileInfo> filteredChildren() {
        try (var codeBaseFiles = Files.walk(rootFile.toPath())) {
            return codeBaseFiles.map(Path::toFile)
                    .filter(file -> new FilePath(file, rootFolder.getValue(), rootFolder.getCodebaseName(), codebasesPath)
                            .isValid())
                    .map(this::child)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } catch (IOException e) {
            throw new CodebasePathCanNotBeRead("Error while reading codebase files tree", e);
        }
    }

    private Optional<FileInfo> child(File codeBaseFile) {
        var filePath = new FilePath(codeBaseFile, rootFolder.getValue(), rootFolder.getCodebaseName(), codebasesPath);
        var path = filePath.pathFromCodebaseName(codeBaseFile);
        var currentNbLines = nbLinesOfCode(codeBaseFile);
        final FileInfo fileInfo = new FileInfo(codeBaseFile.getName(), path, codeBaseFile.isFile(), currentNbLines);
        if (this.filterIgnoredFiles(codeBaseFile, path, fileInfo)) {
            return Optional.of(fileInfo);
        }
        return Optional.empty();
    }

    private long nbLinesOfCode(File codeBaseFile) {
        if (codeBaseFile.isDirectory()) {
            return 0;
        }
        try {
            var lines = Files.readAllLines(codeBaseFile.toPath(), Charset.defaultCharset());
            return lines.size();
        } catch (IOException ex) {
            return 0;
        }
    }

    private boolean filterIgnoredFiles(File currentFile, String path, FileInfo fileInfo) {
        var isIgnored = isIgnored(rootFile.getPath(), path, currentFile.isFile());
        if (isIgnored && currentFile.isFile()) {
            this.ignoredFiles.add(fileInfo);
        }
        return !isIgnored;
    }

    public List<FileInfo> getIgnoredFiles() {
        return ignoredFiles;
    }
}
