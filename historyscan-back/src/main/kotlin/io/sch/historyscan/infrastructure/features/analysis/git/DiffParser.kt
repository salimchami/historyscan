package io.sch.historyscan.infrastructure.features.analysis.git

import io.sch.historyscan.domain.contexts.analysis.history.*
import io.sch.historyscan.infrastructure.features.analysis.FileLinesCount
import io.sch.historyscan.infrastructure.features.analysis.exceptions.CommitDiffException
import org.eclipse.jgit.diff.DiffEntry
import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.lib.FileMode
import org.eclipse.jgit.lib.Repository
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.file.Paths

class DiffParser {
    fun parseDiffs(repository: Repository?, fileDiff: DiffEntry?): CodeBaseHistoryCommitFile {
        try {
            ByteArrayOutputStream().use { out ->
                DiffFormatter(out).use { formatter ->
                    formatter.setRepository(repository)
                    formatter.format(fileDiff)
                    val diffText = out.toString()
                    val diffLines = diffText.split("\r\n|\r|\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val fileContent = fileContentFrom(repository, fileDiff!!.newPath)
                    val linesCount: FileLinesCount = FileLinesCount.Companion.from(diffLines, fileContent, fileDiff.newId.name())
                    val isFile = fileDiff.newMode == FileMode.REGULAR_FILE
                    return CodeBaseHistoryCommitFile(
                            FileInfo(Paths.get(fileDiff.newPath).fileName.toString(),
                                    fileDiff.newPath, isFile), linesCount.nbLines,
                            linesCount.addedLines, linesCount.deletedLines, linesCount.modifiedLines)
                }
            }
        } catch (e: IOException) {
            throw CommitDiffException("Unable to find diff for commit", e)
        }
    }

    @Throws(IOException::class)
    private fun fileContentFrom(repository: Repository?, filePath: String): ByteArray {
        val objectId = repository!!.resolve("HEAD:%s".formatted(filePath)) ?: return ByteArray(0)
        val loader = repository.open(objectId)
        return loader.bytes
    }
}
