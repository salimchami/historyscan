package io.sch.historyscan.infrastructure.features.analysis.git;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;

public record GitCommit(RevCommit commit) {

    public RevTree parentTree() {
        RevTree parentTree;
        if (commit.getParentCount() > 0) {
            parentTree = commit.getParent(0).getTree();
        } else {
            parentTree = null; // No parent for the initial commit
        }
        return parentTree;
    }

}
