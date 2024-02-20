package io.sch.historyscan.fake

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.*
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.RootFolder.Companion.of
import org.springframework.core.io.ClassPathResource
import java.io.IOException

object FileSystemTreeFake {
    @Throws(IOException::class)
    fun create(rootFolderName: String?): FileSystemTree? {
        val codebaseName = "theglobalproject"
        val rootFolder = of(rootFolderName!!, codebaseName)
        val fsTree = FileSystemTree(
            rootFolder
        )
        val codebaseResource = ClassPathResource("codebases/theglobalproject")
        val codebasesResource = ClassPathResource("codebases")
        val rootFile = codebaseResource.file
        fsTree.createFrom(
            CodeBaseFile(
                rootFile, rootFolder,
                codebasesResource.file.path
            )
        )
        return fsTree
            .updateFilesScoreFrom(CodeBaseCommitFake.defaultHistory()!!.commits)
            .then()
            .updateFoldersScore()
    }
}
