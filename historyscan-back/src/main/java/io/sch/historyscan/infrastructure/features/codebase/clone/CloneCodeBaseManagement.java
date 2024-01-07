package io.sch.historyscan.infrastructure.features.codebase.clone;

import io.sch.historyscan.domain.contexts.codebase.clone.ClonedCodeBase;
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseRepository;
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseToClone;
import io.sch.historyscan.infrastructure.features.codebase.exceptions.CloneCodeBaseException;
import io.sch.historyscan.infrastructure.features.codebase.exceptions.CodeBaseAlreadyExistsException;
import io.sch.historyscan.infrastructure.features.codebase.exceptions.PullCodeBaseException;
import io.sch.historyscan.infrastructure.hexagonalarchitecture.HexagonalArchitectureAdapter;
import io.sch.historyscan.infrastructure.logging.AppLogger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.util.FS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static java.lang.String.format;
import static java.util.Collections.singleton;

@Component
@HexagonalArchitectureAdapter
public final class CloneCodeBaseManagement implements CodeBaseRepository {
    private final AppLogger logger;
    private final String codebasesFolder;

    public CloneCodeBaseManagement(AppLogger logger,
                                   @Value("${io.sch.historyscan.codebases.folder}") String codebasesFolder) {
        this.logger = logger;
        this.codebasesFolder = codebasesFolder;
    }

    @Override
    public ClonedCodeBase clone(CodeBaseToClone codeBaseToClone) throws CodeBaseAlreadyExistsException {
        final File codebase = Paths.get(codebasesFolder, codeBaseToClone.name()).toFile();
        if (codebase.exists()) {
            throw new CodeBaseAlreadyExistsException("Repository already exists");
        }
        return clone(codebase, codeBaseToClone);
    }

    private ClonedCodeBase clone(File codebase, CodeBaseToClone codeBaseToClone) {
        try (var git = Git.cloneRepository()
                .setURI(codeBaseToClone.url())
                .setDirectory(codebase)
                .setBranch(codeBaseToClone.branch())
                .setBranchesToClone(singleton("refs/heads/%s".formatted(codeBaseToClone.branch())))
                .setGitDir(new File(codebase, ".git"))
                .setBare(false)
                .call()) {
            logger.info("Cloned repository: " + git.getRepository().getDirectory());
            git.checkout().setName(codeBaseToClone.branch()).call();
            if (!isCodebaseValid(new File(codebase, ".git"))) {
                throw new CloneCodeBaseException(format("Cloned repository is not valid: %s", codeBaseToClone.url()));
            }
            return new ClonedCodeBase(git.getRepository().getConfig().getString("remote", "origin", "url"),
                    codeBaseToClone.name(),
                    codeBaseToClone.branch());
        } catch (GitAPIException e) {
            throw new CloneCodeBaseException(format("Error while cloning repository %s. Maybe it doesn't exist.", codeBaseToClone.url()), e);
        }
    }

    @Override
    public ClonedCodeBase pull(File codebase, CodeBaseToClone codeBaseToClone) {
        try (var localRepo = new FileRepository(codebase);
             var git = new Git(localRepo)) {
            git.pull()
                    .setRemote(codeBaseToClone.url())
                    .setRemoteBranchName(codeBaseToClone.name())
                    .call();
            return new ClonedCodeBase(codeBaseToClone.url(), codeBaseToClone.name(), codeBaseToClone.branch());

        } catch (IOException | GitAPIException e) {
            throw new PullCodeBaseException(format("Error while updating repository %s", codeBaseToClone.url()), e);
        }
    }

    private boolean isCodebaseValid(File codebaseGitFolder) {
        try (var localRepo = new FileRepository(codebaseGitFolder)) {
            return RepositoryCache.FileKey.isGitRepository(codebaseGitFolder, FS.DETECTED)
                    && localRepo.getRefDatabase().getRefs()
                    .stream()
                    .anyMatch(ref -> ref.getObjectId() != null);
        } catch (IOException e) {
            throw new PullCodeBaseException("Error while opening repository", e);
        }
    }
}
