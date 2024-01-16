package io.sch.historyscan.infrastructure.features.analysis.git;

import io.sch.historyscan.infrastructure.features.analysis.exceptions.CommitDiffException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.IOException;
import java.util.List;

public record GitRevTree(Git git, Repository repository, RevTree parentTree, RevTree commitTree) {
        private static final String UNABLE_TO_FIND_DIFF_FOR_COMMIT = "Unable to find diff for commit";


    public List<DiffEntry> diffs() {
        try {
            if (parentTree != null) {
                return git.diff()
                        .setOldTree(prepareTreeParser(repository, parentTree))
                        .setNewTree(prepareTreeParser(repository, commitTree))
                        .call();
            } else {
                return git.diff()
                        .setOldTree(prepareTreeParser(repository, null))
                        .setNewTree(prepareTreeParser(repository, commitTree))
                        .call();
            }
        } catch (GitAPIException e) {
            throw new CommitDiffException(UNABLE_TO_FIND_DIFF_FOR_COMMIT, e);
        }
    }

    private CanonicalTreeParser prepareTreeParser(Repository repository, RevTree tree) {
        try (ObjectReader reader = repository.newObjectReader()) {
            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            if (tree != null) {
                treeParser.reset(reader, tree.getId());
            }
            return treeParser;
        } catch (IOException e) {
            throw new CommitDiffException(UNABLE_TO_FIND_DIFF_FOR_COMMIT, e);
        }
    }

}
