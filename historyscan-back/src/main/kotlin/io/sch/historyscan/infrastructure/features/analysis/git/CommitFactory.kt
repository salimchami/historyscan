package io.sch.historyscan.infrastructure.features.analysis.git

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo
import org.eclipse.jgit.lib.FileMode
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevTree
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class CommitFactory {
    fun createCommit(
        revCommit: RevCommit,
        codebaseCurrentFilesPaths: List<String>,
        gitOperations: GitOperations
    ): CodeBaseCommit? {
        val codeBaseHistoryCommitFiles = commitFilesList(revCommit, gitOperations, codebaseCurrentFilesPaths)
        if (codeBaseHistoryCommitFiles.isEmpty()) {
            return null
        }
        return CodeBaseCommit(
            CodeBaseHistoryCommitInfo(
                revCommit.id.name,
                revCommit.authorIdent.name,
                LocalDateTime.ofInstant(revCommit.authorIdent.whenAsInstant, ZoneId.systemDefault()),
                revCommit.shortMessage
            ),
            codeBaseHistoryCommitFiles.stream().filter { file: CodeBaseHistoryCommitFile -> file.cloc() > 0 }
                .toList()
        )
    }

    private fun commitFilesList(
        gitCommit: RevCommit,
        gitOperations: GitOperations,
        codebaseCurrentFilesPaths: List<String>
    ): List<CodeBaseHistoryCommitFile> {
        val files = ArrayList<CodeBaseHistoryCommitFile>()
        val parentTree = parentTreeFrom(gitCommit)
        val commitTree = gitCommit.tree
        val diffs = gitOperations.commitDiffs(parentTree, commitTree)
        for (fileDiff in diffs) {
            if (fileDiff.newMode.objectType == FileMode.REGULAR_FILE.objectType
                && codebaseCurrentFilesPaths.stream()
                    .anyMatch { codebaseCurrentFile: String -> fileDiff.newPath.contains(codebaseCurrentFile) }
            ) {
                val diffParser = DiffParser()
                val codeBaseHistoryCommitFile = diffParser.parseDiffs(gitOperations.repository, fileDiff)
                files.add(codeBaseHistoryCommitFile)
            }
        }
        return files
    }

    private fun parentTreeFrom(gitCommit: RevCommit): RevTree? {
        if (gitCommit.parentCount > 0) {
            return gitCommit.getParent(0).tree
        }
        return null
    }
}
