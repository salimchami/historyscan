package io.sch.historyscan.infrastructure.features.codebase.clone

import io.sch.historyscan.domain.contexts.codebase.clone.ClonedCodeBase
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseAlreadyExistsException
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseRepository
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseToClone
import io.sch.historyscan.infrastructure.features.codebase.exceptions.CloneCodeBaseException
import io.sch.historyscan.infrastructure.features.codebase.exceptions.PullCodeBaseException
import io.sch.historyscan.infrastructure.hexagonalarchitecture.HexagonalArchitectureAdapter
import io.sch.historyscan.infrastructure.logging.AppLogger
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.RepositoryCache
import org.eclipse.jgit.util.FS
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.io.IOException
import java.nio.file.Paths

@Component
@HexagonalArchitectureAdapter
class CloneCodeBaseManagement(private val logger: AppLogger,
                              @param:Value("\${io.sch.historyscan.codebases.folder}") private val codebasesFolder: String) : CodeBaseRepository {
    @Throws(CodeBaseAlreadyExistsException::class)
    override fun clone(codeBaseToClone: CodeBaseToClone): ClonedCodeBase {
        val codebase = Paths.get(codebasesFolder, codeBaseToClone.name).toFile()
        if (codebase.exists()) {
            throw CodeBaseAlreadyExistsException("Repository already exists")
        }
        return clone(codebase, codeBaseToClone)
    }

    private fun clone(codebase: File, codeBaseToClone: CodeBaseToClone): ClonedCodeBase {
        try {
            Git.cloneRepository()
                    .setURI(codeBaseToClone.url)
                    .setDirectory(codebase)
                    .setBranch(codeBaseToClone.branch)
                    .setBranchesToClone(setOf("refs/heads/%s".format(codeBaseToClone.branch)))
                    .setGitDir(File(codebase, ".git"))
                    .setBare(false)
                    .call().use { git ->
                        logger.info("Cloned repository: " + git.repository.directory)
                        git.checkout().setName(codeBaseToClone.branch).call()
                        if (!isCodebaseValid(File(codebase, ".git"))) {
                            throw CloneCodeBaseException(String.format("Cloned repository is not valid: %s", codeBaseToClone.url))
                        }
                        return ClonedCodeBase(git.repository.config.getString("remote", "origin", "url"),
                                codeBaseToClone.name,
                                codeBaseToClone.branch)
                    }
        } catch (e: GitAPIException) {
            throw CloneCodeBaseException(String.format("Error while cloning repository %s. Maybe it doesn't exist.", codeBaseToClone.url), e)
        }
    }

    override fun pull(codebase: File, codeBaseToClone: CodeBaseToClone): ClonedCodeBase {
        try {
            FileRepository(codebase).use { localRepo ->
                Git(localRepo).use { git ->
                    git.pull()
                            .setRemote(codeBaseToClone.url)
                            .setRemoteBranchName(codeBaseToClone.name)
                            .call()
                    return ClonedCodeBase(codeBaseToClone.url, codeBaseToClone.name, codeBaseToClone.branch)
                }
            }
        } catch (e: IOException) {
            throw PullCodeBaseException(String.format("Error while updating repository %s", codeBaseToClone.url), e)
        } catch (e: GitAPIException) {
            throw PullCodeBaseException(String.format("Error while updating repository %s", codeBaseToClone.url), e)
        }
    }

    private fun isCodebaseValid(codebaseGitFolder: File): Boolean {
        try {
            FileRepository(codebaseGitFolder).use { localRepo ->
                return (RepositoryCache.FileKey.isGitRepository(codebaseGitFolder, FS.DETECTED)
                        && localRepo.refDatabase.refs
                        .stream()
                        .anyMatch { ref: Ref -> ref.objectId != null })
            }
        } catch (e: IOException) {
            throw PullCodeBaseException("Error while opening repository", e)
        }
    }
}
