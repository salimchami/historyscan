package io.sch.historyscan.infrastructure.features.filesystem;

import io.sch.historyscan.domain.contexts.codebase.CodeBaseToClone;
import io.sch.historyscan.infrastructure.features.codebase.CodeBaseManagementRepository;
import io.sch.historyscan.infrastructure.features.codebase.CreatingCodeBaseFolderException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileSystemManager {
    private final CodeBaseManagementRepository codeBaseManagementRepository;

    public FileSystemManager(CodeBaseManagementRepository codeBaseManagementRepository) {
        this.codeBaseManagementRepository = codeBaseManagementRepository;
    }

    public File create(CodeBaseToClone codeBaseToClone) {
        var path = Paths.get(codeBaseManagementRepository.codebasesFolder(), codeBaseToClone.name());
        if (!path.toFile().exists()) {
            try {
                return Files.createDirectories(path).toFile();
            } catch (IOException e) {
                throw new CreatingCodeBaseFolderException(String.format("Error while creating folder %s", path), e);
            }
        }
        return new File(codeBaseManagementRepository.codebasesFolder());
    }
}