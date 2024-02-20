package io.sch.historyscan.common

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.revwalk.RevCommit
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Path

abstract class GitTest {
    lateinit var git: Git
    lateinit var codebase: File
    lateinit var gitCommit: RevCommit

    @BeforeEach
    @Throws(GitAPIException::class, IOException::class)
    fun setUp(@TempDir tempDir: Path) {
        codebase = tempDir.toFile()
        git = Git.init().setDirectory(codebase).call()
        gitCommit = commitFile()
    }

    @AfterEach
    fun tearDown() {
        git.repository.close()
    }

    @Throws(IOException::class, GitAPIException::class)
    private fun commitFile(): RevCommit {
        val file = writeFile()
        git.add().addFilepattern(file.name).call()
        return git.commit().setMessage("commit message").setSign(false).call()
    }

    @Throws(IOException::class)
    private fun writeFile(): File {
        val file = File(git.repository.workTree, "file1.txt")
        FileOutputStream(file).use { outputStream ->
            outputStream.write(
                "content1\ncontent2\ncontent3".toByteArray(
                    StandardCharsets.UTF_8
                )
            )
        }
        return file
    }
}
