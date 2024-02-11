package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

import io.sch.historyscan.domain.contexts.analysis.common.EnumIgnoredCodeBaseFiles
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class CodeBaseFile(private val rootFile: File, private val rootFolder: RootFolder, private val codebasesPath: String) {
    val ignoredFiles: MutableList<FileInfo> = ArrayList()

    fun hasSameNameAsCodeBase(): Boolean {
        return rootFile.name == rootFolder.codebaseName
    }

    fun filteredChildren(): List<FileInfo> {
        try {
            Files.walk(rootFile.toPath()).use { codeBaseFiles ->
                return codeBaseFiles.map { obj: Path -> obj.toFile() }
                        .filter { file: File ->
                            FilePath(file, rootFolder.value, rootFolder.codebaseName, codebasesPath)
                                    .isValid
                        }
                        .map { codeBaseFile: File -> this.child(codeBaseFile) }
                        .map { it!! }
                        .toList()
            }
        } catch (e: IOException) {
            throw CodebasePathCanNotBeRead("Error while reading codebase files tree", e)
        }
    }

    private fun child(codeBaseFile: File): FileInfo? {
        val filePath = FilePath(codeBaseFile, rootFolder.value, rootFolder.codebaseName, codebasesPath)
        val path = filePath.pathFromCodebaseName(codeBaseFile)
        val currentNbLines = nbLinesOfCode(codeBaseFile)
        val fileInfo = FileInfo(codeBaseFile.name, path, codeBaseFile.isFile, currentNbLines)
        if (this.filterIgnoredFiles(codeBaseFile, path, fileInfo)) {
            return fileInfo
        }
        return null
    }

    private fun nbLinesOfCode(codeBaseFile: File): Long {
        if (codeBaseFile.isDirectory) {
            return 0
        }
        try {
            val lines = Files.readAllLines(codeBaseFile.toPath(), Charset.defaultCharset())
            return lines.size.toLong()
        } catch (ex: IOException) {
            return 0
        }
    }

    private fun filterIgnoredFiles(currentFile: File, path: String, fileInfo: FileInfo): Boolean {
        val isIgnored: Boolean = EnumIgnoredCodeBaseFiles.isIgnored(rootFile.path, path, currentFile.isFile)
        if (isIgnored && currentFile.isFile) {
            ignoredFiles.add(fileInfo)
        }
        return !isIgnored
    }
}
