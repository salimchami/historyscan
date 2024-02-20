package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

import java.io.File
import java.util.*

class FilePath(
    val currentFile: File,
    val rootFolder: String,
    val codebaseName: String,
    val codebasesPath: String
) {
    fun pathFromCodebaseName(currentFile: File): String {
        val fullPath = sanitizePath(Objects.requireNonNull(currentFile).path)
        val projectPath = sanitizePath(fullPath.substring(0, codebasesPath.length))
        val filePath = fullPath.substring(projectPath.length)
        return sanitizePath(filePath)
    }

    val isValid: Boolean
        get() {
            val filePath = sanitizePath(currentFile.path)
            val isGitFolder = filePath.contains("/.git")
            val isCodebasesPath = filePath == sanitizePath(codebasesPath)
            val isCodebasePath = filePath == sanitizePath(codebasesPath) + "/" + codebaseName
            val containsRootFolder = filePath.contains(sanitizePath(rootFolder))
            return !isGitFolder && !isCodebasesPath && !isCodebasePath && containsRootFolder
        }

    private fun sanitizePath(path: String): String {
        var sanitizedPAth = path.replace("\\", "/")
        if (sanitizedPAth.endsWith("/")) {
            sanitizedPAth = sanitizedPAth.substring(0, path.length - 1)
        }
        if (sanitizedPAth.startsWith("/")) {
            sanitizedPAth = sanitizedPAth.substring(1)
        }
        return sanitizedPAth
    }

    companion object {
        fun commonPartOf(firstPath: String, secondPath: String): String {
            val firstChar = secondPath.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            val firstPathSplitIndex = firstPath.lastIndexOf(firstChar)
            val commonPart = firstPath.substring(firstPathSplitIndex)
            return secondPath.substring(0, commonPart.length)
        }
    }
}
