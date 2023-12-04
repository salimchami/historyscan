package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClusteredClocRevisions {

    private final RootFolder rootFolder;
    private final List<CodebaseFileClocRevisions> sortedRevisions;

    public ClusteredClocRevisions(RootFolder rootFolder, List<CodebaseFileClocRevisions> sortedRevisions) {
        this.rootFolder = rootFolder;
        this.sortedRevisions = sortedRevisions;
    }

    public List<List<CodebaseFileClocRevisions>> toClusters() {
        Map<String, List<CodebaseFileClocRevisions>> revisionsByDirectory = new HashMap<>();
        sortedRevisions.forEach(revision -> {
            String relativePath = revision.fileName().replace(rootFolder.value(), "");
            String directory = relativePath.contains("/") ? relativePath.substring(0, relativePath.lastIndexOf('/')) : "/";
            revisionsByDirectory.computeIfAbsent(directory, k -> new ArrayList<>())
                    .add(new CodebaseFileClocRevisions(relativePath, revision.numberOfRevisions(),
                            revision.nbLines(), revision.score()));
        });
        return new ArrayList<>(revisionsByDirectory.values());
    }
}