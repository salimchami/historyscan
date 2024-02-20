package io.sch.historyscan.infrastructure.features.codebase.info

import io.sch.historyscan.domain.contexts.codebase.find.CodeBaseInfoInventory
import io.sch.historyscan.domain.contexts.codebase.find.CurrentCodeBase
import io.sch.historyscan.infrastructure.common.filesystem.FileSystemManager
import io.sch.historyscan.infrastructure.features.analysis.exceptions.CodeBaseHeadNotFoundException
import io.sch.historyscan.infrastructure.hexagonalarchitecture.HexagonalArchitectureAdapter
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.errors.RepositoryNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File

@Component
@HexagonalArchitectureAdapter
class CodeBaseInfoManagement(
    @param:Value("\${io.sch.historyscan.codebases.folder}") private val codebasesFolder: String,
    private val fileSystemManager: FileSystemManager
) : CodeBaseInfoInventory {
    override fun findBy(name: String): CurrentCodeBase {
        val folder = fileSystemManager.findFolder(codebasesFolder, name)
        return folder?.let { codeBaseFromFolder(it) }
            ?: CurrentCodeBase(name, null, null, true)
    }


    fun codeBaseFromFolder(folder: File): CurrentCodeBase {
        val gitFolder = GitFolder(folder).toDotGitFolder()
        try {
            Git.open(gitFolder).use { git ->
                return CurrentCodeBase(
                    folder.name,
                    git.repository.config.getString("remote", "origin", "url"),
                    git.repository.branch,
                    false
                )
            }
        } catch (e: RepositoryNotFoundException) {
            throw CodeBaseHeadNotFoundException("The folder %s is not a git repository".format(folder.name), e)
        } catch (e: Exception) {
            return CurrentCodeBase(folder.name, null, null, true)
        }
    }
}
