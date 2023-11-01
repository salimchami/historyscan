package io.sch.historyscan.infrastructure.features.analysis;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;

public class GitCommit {
    private final RevCommit revCommit;

    public GitCommit(RevCommit revCommit) {
        this.revCommit = revCommit;
    }

    public RevTree parentTree() {
        if (revCommit.getParentCount() > 0) {
            return revCommit.getParent(0).getTree();
        } else {
            return null;
        }

    }

    public RevTree getTree() {
        return revCommit.getTree();
    }

    public String getName() {
        return revCommit.getName();
    }
}
