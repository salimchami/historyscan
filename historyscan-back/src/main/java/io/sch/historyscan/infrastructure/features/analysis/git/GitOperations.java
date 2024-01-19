package io.sch.historyscan.infrastructure.features.analysis.git;

import io.sch.historyscan.infrastructure.features.analysis.exceptions.CommitDiffException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.StreamSupport;

public class GitOperations implements AutoCloseable {
    private final Repository repository;
    private final Git git;

    public GitOperations(File codebase) throws IOException {
        this.repository = new FileRepositoryBuilder().setGitDir(Paths.get(codebase.toString(), ".git").toFile()).build();
        this.git = new Git(repository);
    }

    public List<RevCommit> getCommits() throws GitAPIException, IOException {
        git.fetch().call();
        git.pull().call();
        return StreamSupport.stream(git.log().all().call().spliterator(), false).toList();
    }

    public List<DiffEntry> commitDiffs(RevTree parentTree, RevTree commitTree) {
        try {
            if (parentTree != null) {
                return git.diff()
                        .setOldTree(prepareTreeParser(parentTree))
                        .setNewTree(prepareTreeParser(commitTree))
                        .call();
            } else {
                return git.diff()
                        .setOldTree(prepareTreeParser(null))
                        .setNewTree(prepareTreeParser(commitTree))
                        .call();
            }
        } catch (GitAPIException e) {
            throw new CommitDiffException("Unable to find diff for commit.", e);
        }

    }

    private CanonicalTreeParser prepareTreeParser(RevTree tree) {
        try (ObjectReader reader = repository.newObjectReader()) {
            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            if (tree != null) {
                treeParser.reset(reader, tree.getId());
            }
            return treeParser;
        } catch (IOException e) {
            throw new CommitDiffException("Unable to find diff for commit.", e);
        }
    }

    public void close() {
        git.close();
        repository.close();
    }


    public Repository getRepository() {
        return repository;
    }
}
