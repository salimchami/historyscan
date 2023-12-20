package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static io.sch.historyscan.domain.contexts.analysis.clocrevisions.PathsUtils.normalizeFolder;
import static io.sch.historyscan.domain.contexts.analysis.clocrevisions.PathsUtils.normalizePath;

public class ClusteredClocRevisions {

    private final List<ClocRevisionsFile> sortedRevisions;

    public ClusteredClocRevisions(List<ClocRevisionsFile> sortedRevisions) {
        this.sortedRevisions = sortedRevisions;
    }

    public List<ClocRevisionsFileCluster> toClusters(String rootFolder) {
        var normalizedRootFolder = normalizeFolder(rootFolder);
        Map<String, List<ClocRevisionsFile>> fileGroups = new HashMap<>();

        for (ClocRevisionsFile file : sortedRevisions) {
            String relativePath = getRelativePath(file.filePath(), normalizedRootFolder);
            String folder = extractFolderName(relativePath, normalizedRootFolder);

            fileGroups.computeIfAbsent(folder, k -> new ArrayList<>()).add(file);
        }

        List<ClocRevisionsFileCluster> clusters = new ArrayList<>();
        fileGroups.forEach((folder, files) -> clusters.add(new ClocRevisionsFileCluster(folder, files)));
        return clusters;
    }

    private String getRelativePath(String filePath, String rootFolder) {
        String normalizedPath = normalizePath(filePath);
        if (normalizedPath.contains(rootFolder + "/")) {
            return normalizedPath.split(Pattern.quote(rootFolder + "/"))[1];
        } else {
            return "";
        }
    }

    private String extractFolderName(String relativePath, String rootFolder) {
        String[] parts = relativePath.split("/");
        return parts.length > 1 ? parts[0] : rootFolder;
    }
}