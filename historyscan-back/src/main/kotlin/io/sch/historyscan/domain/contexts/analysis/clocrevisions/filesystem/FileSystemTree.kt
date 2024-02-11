package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.RevisionScore
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile
import io.sch.historyscan.domain.hexagonalarchitecture.DDDEntity
import java.util.*
import java.util.stream.Collectors

@DDDEntity
class FileSystemTree(val rootFolder: RootFolder) {
    @JvmField
    val root: FileSystemNode = FileSystemNode("root", "/", null, false, 0, RevisionScore(0))
    private val ignoredFiles: MutableList<FileInfo> = ArrayList()

    fun createFrom(codeBaseFile: CodeBaseFile) {
        require(codeBaseFile.hasSameNameAsCodeBase()) { "Codebase must be the same as the root folder name" }
        val filteredChildren = codeBaseFile.filteredChildren()
        for (nestedCodeBaseFile in filteredChildren) {
            val parts = nestedCodeBaseFile.pathParts(rootFolder.value)
            addFileParts(parts, nestedCodeBaseFile)
        }
        ignoredFiles.addAll(codeBaseFile.ignoredFiles)
    }

    private fun addFileParts(codeBaseParts: List<String>, file: FileInfo) {
        var current = root
        for (i in codeBaseParts.indices) {
            val part = codeBaseParts[i]
            if (!current.children.containsKey(part)) {
                val parentPath = parentPath(file, i, part)
                val isFile = file.isFileFrom(part, rootFolder.value)
                val newNode = FileSystemNode(
                    part,
                    file.pathFrom(part),
                    parentPath,
                    isFile,
                    if (isFile) file.currentNbLines else 0,
                    RevisionScore(0)
                )
                current.addChild(part, newNode)
            }
            current = current.findChild(part)
        }
    }

    private fun parentPath(file: FileInfo, i: Int, part: String): String? {
        if (file.path.lastIndexOf(part) == 0 || rootFolder.value == part) {
            return "/"
        }
        return if (i == 0) null else file.parentPathFrom(part)
    }

    fun updateFilesScoreFrom(history: List<CodeBaseCommit>): FileSystemTree {
        history.stream()
            .flatMap { commit: CodeBaseCommit -> commit.files.stream() }
            .collect(Collectors.groupingBy(CodeBaseHistoryCommitFile::fileInfo))
            .forEach { (key: io.sch.historyscan.domain.contexts.analysis.history.FileInfo, value: List<CodeBaseHistoryCommitFile>) ->
                val revisions = value.stream()

                    .map { obj: CodeBaseHistoryCommitFile -> obj.cloc() }
                    .reduce(0) { a: Int, b: Int -> Integer.sum(a, b) }
                    .toLong()
                root.findFileNode(key.path)?.updateScoreFrom(RevisionScore(revisions))
            }
        return this
    }

    fun then(): FileSystemTree {
        return this
    }

    fun updateFoldersScore(): FileSystemTree {
        root.updateFolderScore()
        return this
    }

    fun ignoredRevisions(): List<FileInfo> {
        return ignoredFiles
    }

    fun extensions(): List<String> {
        return FileSystemNodeExtension(root)
            .allExtensions
    }

    fun files(): List<FileSystemNode> {
        return root.allFileNodes()
    }
}
