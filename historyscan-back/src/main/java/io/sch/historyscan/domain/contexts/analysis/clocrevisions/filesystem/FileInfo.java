package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public record FileInfo(String name, String path, boolean isFile, long currentNbLines) implements Comparable<FileInfo> {
    public List<String> pathParts(String rootFolder) {
        if (rootFolder == null || rootFolder.isEmpty()) {
            return Arrays.stream(path.trim().split("/"))
                    .filter(part -> !part.isEmpty())
                    .toList();
        }
        var pathParts = Arrays.stream(path.trim().split(rootFolder + "/", -1))
                .toList();
        return Stream.concat(
                        pathPartsRootFolderWithPrefix(rootFolder, pathParts),
                        pathPartsRootFolderSuffix(pathParts)
                )
                .filter(part -> !part.isEmpty())
                .toList();
    }

    private static Stream<String> pathPartsRootFolderSuffix(List<String> splitPath) {
        return Arrays.stream(splitPath.get(splitPath.size() - 1).split("/"))
                .filter(subPart -> !subPart.isEmpty());
    }

    private static Stream<String> pathPartsRootFolderWithPrefix(String rootFolder, List<String> splitPath) {
        return splitPath.stream()
                .limit(splitPath.size() - 1L)
                .flatMap(part -> Stream.concat(
                        Arrays.stream(part.split("/"))
                                .filter(subPart -> !subPart.isEmpty()),
                        Stream.of(rootFolder)
                ));
    }

    public String pathFrom(String pathPart) {
        int index = pathPart.equals(name) || (pathPart.contains("/") && pathPart.endsWith(name))
                ? path.lastIndexOf(pathPart)
                : path.lastIndexOf(pathPart + "/");
        return path.substring(0, index == 0 ? pathPart.length() : index + pathPart.length());
    }

    public boolean isFileFrom(String pathPart, String rootFolder) {
        List<String> parts = pathParts(rootFolder);
        int partIndex = parts.indexOf(pathPart);
        if (partIndex == -1 || partIndex != parts.size() - 1) {
            return false;
        }
        return !path.endsWith("/") && isFile;
    }

    @Override
    public int compareTo(FileInfo o) {
        return comparing(FileInfo::path)
                .thenComparing(FileInfo::name)
                .compare(this, o);
    }

    public String parentPathFrom(String pathPart) {
        int index = pathPart.equals(name)
                ? path.lastIndexOf(pathPart)
                : path.lastIndexOf(pathPart + "/");
        if (index == 0) {
            return null;
        }
        return path.substring(0, index - 1);
    }
}
