package io.sch.historyscan.infrastructure.common.filesystem;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public record FileSystemManager() {

    public List<File> listFoldersFrom(String folder) {
        return Arrays.stream(Objects.requireNonNull(new File(folder).listFiles(File::isDirectory)))
                .toList();
    }

    public Optional<File> findFolder(String baseFolder, String folderName) {
        return listFoldersFrom(baseFolder)
                .stream()
                .filter(folder -> folder.getName().equals(folderName) || folder.getName().endsWith(folderName))
                .findFirst();
    }

    public List<String> allFilesFrom(File codebase, String ignoredFolder) {
        final Path codebasPath = Paths.get(codebase.getAbsolutePath());
        try (var files = Files.walk(codebasPath)) {
            return files.filter(path -> !path.toFile().isDirectory())
                    .filter(path -> !path.startsWith(codebasPath.resolve(ignoredFolder)))
                    .map(Path::toFile)
                    .map(File::getName)
                    .toList();

        } catch (IOException e) {
            throw new FolderNotReadableException("unable to read folder %s".formatted(codebase), e);
        }
    }
}