package io.sch.historyscan.infrastructure.features.filesystem;

import io.sch.historyscan.infrastructure.features.codebase.clone.CreatingCodeBaseFolderException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public record FileSystemManager() {
    public File create(String folder) {
        var path = Paths.get(folder);
        if (!path.toFile().exists()) {
            try {
                return Files.createDirectories(path).toFile();
            } catch (IOException e) {
                throw new CreatingCodeBaseFolderException(String.format("Error while creating folder %s", path), e);
            }
        }
        return path.toFile();
    }

    public List<File> listFoldersFrom(String folder) {
        return Arrays.stream(Objects.requireNonNull(new File(folder).listFiles(File::isDirectory)))
                .toList();
    }
}