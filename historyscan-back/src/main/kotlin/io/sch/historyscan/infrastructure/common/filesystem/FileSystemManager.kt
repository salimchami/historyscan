package io.sch.historyscan.infrastructure.common.filesystem

import org.springframework.stereotype.Component
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Component
class FileSystemManager {
    fun listFoldersFrom(folder: String?): List<File> {
        return Arrays.stream(Objects.requireNonNull(folder?.let { File(it).listFiles { obj: File -> obj.isDirectory } }))
            .toList()
    }

    fun findFolder(baseFolder: String?, folderName: String): File? {
        return listFoldersFrom(baseFolder)
            .firstOrNull { folder -> folder.name == folderName || folder.name.endsWith(folderName) }

    }

    fun allFilesFrom(codebase: File?, ignoredFolder: String): List<String> {
        val codebasPath = Paths.get(codebase!!.absolutePath)
        try {
            Files.walk(codebasPath).use { files ->
                return files.filter { path: Path -> !path.toFile().isDirectory }
                    .filter { path: Path -> !path.startsWith(codebasPath.resolve(ignoredFolder)) }
                    .map { obj: Path -> obj.toFile() }
                    .map { obj: File -> obj.name }
                    .toList()
            }
        } catch (e: IOException) {
            throw FolderNotReadableException("unable to read folder %s".format(codebase), e)
        }
    }
}