package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemTree;

import java.util.List;

public class ClusteredClocRevisions {

    private final List<ClocRevisionsFile> sortedRevisions;

    public ClusteredClocRevisions(List<ClocRevisionsFile> sortedRevisions) {
        this.sortedRevisions = sortedRevisions;
    }

    public FileSystemTree toFileSystemTree(String rootFolder) {
        var fsTree = new FileSystemTree(rootFolder);
        sortedRevisions.forEach(fsTree::addFile);
        fsTree.updateFoldersScore();
        return fsTree;
    }

}