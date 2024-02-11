package io.sch.historyscan.infrastructure.features.analysis.git

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.revwalk.RevCommit
import java.io.File
import java.io.IOException

class GitWrapper(private val codebase: File, private val codebaseCurrentFilesPaths: List<String>) {
    private val commitFactory = CommitFactory()

    @Throws(GitAPIException::class, IOException::class)
    fun history(): CodeBaseHistory {
        GitOperations(codebase).use { gitOperations ->
            return CodeBaseHistory(
                gitOperations.commits
                    .mapNotNull { revCommit: RevCommit ->
                        commitFactory.createCommit(revCommit, codebaseCurrentFilesPaths, gitOperations)
                    }
                    .toList())
        }
    }
}
