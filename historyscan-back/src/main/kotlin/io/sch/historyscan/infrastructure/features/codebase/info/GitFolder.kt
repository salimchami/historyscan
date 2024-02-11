package io.sch.historyscan.infrastructure.features.codebase.info

import java.io.File

class GitFolder(codebaseRootFolder: File?) {
    fun toDotGitFolder(): File {
        if (codebaseRootFolder!!.isDirectory && codebaseRootFolder.name != ".git") {
            return File(codebaseRootFolder, ".git")
        } else if (codebaseRootFolder.isFile) {
            return File(codebaseRootFolder.parent, ".git")
        }
        throw CodeBaseGitFolderNotFoundException("The codebase root folder is not a file or a directory")
    }

    val codebaseRootFolder: File?

    init {
        this.nbLines = nbLines
        this.addedLines = addedLines
        this.deletedLines = deletedLines
        this.modifiedLines = modifiedLines
        this.codebaseRootFolder = codebaseRootFolder
    }
}
