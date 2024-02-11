package io.sch.historyscan.infrastructure.features.analysis.git

import io.sch.historyscan.infrastructure.features.analysis.exceptions.CommitDiffException
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.diff.DiffEntry
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevTree
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.treewalk.CanonicalTreeParser
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import java.util.stream.StreamSupport

class GitOperations(codebase: File?) : AutoCloseable {
    val repository: Repository = FileRepositoryBuilder().setGitDir(Paths.get(codebase.toString(), ".git").toFile()).build()
    private val git = Git(repository)

    @get:Throws(GitAPIException::class, IOException::class)
    val commits: List<RevCommit>
        get() {
            git.fetch().call()
            git.pull().call()
            return StreamSupport.stream(git.log().all().call().spliterator(), false).toList()
        }

    fun commitDiffs(parentTree: RevTree?, commitTree: RevTree): List<DiffEntry> {
        try {
            return if (parentTree != null) {
                git.diff()
                        .setOldTree(prepareTreeParser(parentTree))
                        .setNewTree(prepareTreeParser(commitTree))
                        .call()
            } else {
                git.diff()
                        .setOldTree(prepareTreeParser(null))
                        .setNewTree(prepareTreeParser(commitTree))
                        .call()
            }
        } catch (e: GitAPIException) {
            throw CommitDiffException("Unable to find diff for commit.", e)
        }
    }

    private fun prepareTreeParser(tree: RevTree?): CanonicalTreeParser {
        try {
            repository.newObjectReader().use { reader ->
                val treeParser = CanonicalTreeParser()
                if (tree != null) {
                    treeParser.reset(reader, tree.id)
                }
                return treeParser
            }
        } catch (e: IOException) {
            throw CommitDiffException("Unable to find diff for commit.", e)
        }
    }

    override fun close() {
        git.close()
        repository.close()
    }
}
