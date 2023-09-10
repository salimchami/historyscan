package io.sch.historyscan.infrastructure.features.codebase.clone;

import io.sch.historyscan.domain.contexts.codebase.clone.ClonedCodeBase;
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseRepository;
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseToClone;
import io.sch.historyscan.infrastructure.features.codebase.errors.CloneCodeBaseException;
import io.sch.historyscan.infrastructure.features.codebase.errors.PullCodeBaseException;
import io.sch.historyscan.infrastructure.features.filesystem.FileSystemManager;
import io.sch.historyscan.infrastructure.logging.AppLogger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static java.lang.String.format;

@Component
public final class CloneCodeBaseManagement implements CodeBaseRepository {
    private final AppLogger logger;
    private final String codebasesFolder;
    private final FileSystemManager fileSystemManager;

    public CloneCodeBaseManagement(AppLogger logger,
                                   @Value("${io.sch.historyscan.codebases.folder}") String codebasesFolder,
                                   FileSystemManager fileSystemManager) {
        this.logger = logger;
        this.codebasesFolder = codebasesFolder;
        this.fileSystemManager = fileSystemManager;
    }

    @Override
    public ClonedCodeBase clone(CodeBaseToClone codeBaseToClone) {
        final File codebase = fileSystemManager.create(Paths.get(codebasesFolder, codeBaseToClone.name()).toString());
        if (codebase.exists()) {
            return pull(codebase, codeBaseToClone);
        } else {
            return clone(codebase, codeBaseToClone);
        }
    }

    private ClonedCodeBase clone(File codebase, CodeBaseToClone codeBaseToClone) {
        try (var git = Git.cloneRepository()
                .setURI(codeBaseToClone.url())
                .setDirectory(codebase)
                .call()) {
            logger.debug("Cloned repository: " + git.getRepository().getDirectory());
            return new ClonedCodeBase(git.getRepository().getConfig().getString("remote", "origin", "url"),
                    git.getRepository().getDirectory().toString(),
                    codeBaseToClone.branch());
        } catch (GitAPIException e) {
            throw new CloneCodeBaseException(format("Error while cloning repository %s", codeBaseToClone.url()), e);
        }
    }

    private ClonedCodeBase pull(File codebase, CodeBaseToClone codeBaseToClone) {
        try {
            var localRepo = new FileRepository(codebase + "/.git");
            var git = new Git(localRepo);
            git.pull();
            git.close();
            return new ClonedCodeBase(codeBaseToClone.url(), codeBaseToClone.name(), codeBaseToClone.branch());
        } catch (IOException e) {
            throw new PullCodeBaseException(format("Error while cloning repository %s", codeBaseToClone.url()), e);
        }
    }
}
