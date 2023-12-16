package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import java.util.*;

public class ClusteredClocRevisions {

    private final List<ClocRevisionsFile> sortedRevisions;

    public ClusteredClocRevisions(List<ClocRevisionsFile> sortedRevisions) {
        this.sortedRevisions = sortedRevisions;
    }

    public List<List<ClocRevisionsFile>> toClusters() {
        Map<String, List<ClocRevisionsFile>> sortedFiles = new HashMap<>();

        for (var file : sortedRevisions) {
            String path = file.file().path();
            String[] pathParts = path.split("/");
            String directoryName = pathParts[pathParts.length - 2];

            sortedFiles.computeIfAbsent(directoryName, k -> new ArrayList<>()).add(file);
        }
        final List<Map.Entry<String, List<ClocRevisionsFile>>> sortedEntries = reorder(sortedFiles);

        return sortedEntries.stream().map(Map.Entry::getValue).map(list -> list.stream().sorted().toList()).toList();
    }

    private static List<Map.Entry<String, List<ClocRevisionsFile>>> reorder(Map<String, List<ClocRevisionsFile>> sortedFiles) {
        sortedFiles.values().forEach(fileList ->
                fileList.sort(Comparator.comparing(f -> f.file().path())));

        var directoryFrequency = directoryFrequency(sortedFiles);

        return sortedFiles.entrySet()
                .stream()
                .sorted(Map.Entry.<String, List<ClocRevisionsFile>>comparingByKey(Comparator.comparingInt(directoryFrequency::get))
                        .thenComparing(Map.Entry.comparingByKey()))
                .toList();
    }

    private static Map<String, Integer> directoryFrequency(Map<String, List<ClocRevisionsFile>> sortedFiles) {
        Map<String, Integer> directoryFrequency = new HashMap<>();
        for (String directory : sortedFiles.keySet()) {
            directoryFrequency.put(directory, directoryFrequency.getOrDefault(directory, 0) + 1);
        }
        return directoryFrequency;
    }
}