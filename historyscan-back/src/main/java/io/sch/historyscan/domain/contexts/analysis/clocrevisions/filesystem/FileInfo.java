package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;

public record FileInfo(String name,
                       String path, boolean isFile, long currentNbLines) implements Comparable<FileInfo> {
    public List<String> pathParts() {
        return Arrays.stream(path.trim().split("/")).filter(part -> !part.isEmpty()).toList();
    }

    public String pathFrom(String pathPart) {
        int index = pathPart.equals(name)
                ? path.lastIndexOf(pathPart)
                : path.lastIndexOf(pathPart + "/");
        return path.substring(0, index == 0 ? pathPart.length() : index + pathPart.length());
    }

    public boolean isFileFrom(String pathPart) {
        List<String> parts = pathParts();
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
