package io.sch.historyscan.infrastructure.features.codebase;

import io.sch.historyscan.domain.contexts.codebase.ClonedCodeBase;
import io.sch.historyscan.domain.contexts.codebase.CodeBaseRepository;
import io.sch.historyscan.domain.contexts.codebase.CodeBaseToClone;
import io.sch.historyscan.infrastructure.features.codebase.errors.CloningCodeBaseException;
import io.sch.historyscan.infrastructure.features.filesystem.FileSystemManager;
import io.sch.historyscan.infrastructure.logging.AppLogger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

import static java.lang.String.format;

@Component
public record CodeBaseManagementRepository(AppLogger logger,
                                           @Value("${io.sch.historyscan.codebases.folder}") String codebasesFolder,
                                           FileSystemManager fileSystemManager) implements CodeBaseRepository {
    @Override
    public ClonedCodeBase clone(CodeBaseToClone codeBaseToClone) {
        final File localPath = fileSystemManager.create(codeBaseToClone);
        try (
                var git = Git.cloneRepository()
                        .setURI(codeBaseToClone.url())
                        .setDirectory(localPath)
                        .call()) {
            logger.info("Cloned repository: " + git.getRepository().getDirectory());
            return new ClonedCodeBase(git.remoteSetUrl().toString(), git.getRepository().getDirectory().toString(),
                    codeBaseToClone.branch());
        } catch (GitAPIException e) {
            throw new CloningCodeBaseException(format("Error while cloning repository %s", codeBaseToClone.url()), e);
        }
    }

}
