package io.sch.historyscan.common;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class GitTest {
    protected Git git;
    protected File codebase;
    protected RevCommit gitCommit;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) throws GitAPIException, IOException {
        codebase = tempDir.toFile();
        git = Git.init().setDirectory(codebase).call();
        gitCommit = commitFile();
    }

    @AfterEach
    public void tearDown() {
        git.getRepository().close();
    }

    private RevCommit commitFile() throws IOException, GitAPIException {
        var file = writeFile();
        git.add().addFilepattern(file.getName()).call();
        return git.commit().setMessage("commit message").setSign(false).call();
    }

    private File writeFile() throws IOException {
        File file = new File(git.getRepository().getWorkTree(), "file1.txt");
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write("content1\ncontent2\ncontent3".getBytes(UTF_8));
        }
        return file;
    }
}
