package io.sch.historyscan.domain.contexts.analysis.history

class CodeBaseHistoryCommitFile(
    val fileInfo: FileInfo,
    val currentNbLines: Int,
    val nbAddedLines: Int,
    val nbDeletedLines: Int,
    val nbModifiedLines: Int
) {
    fun cloc(): Int {
        return nbAddedLines + nbDeletedLines + nbModifiedLines
    }

    fun path(): String {
        return fileInfo.path
    }
}
