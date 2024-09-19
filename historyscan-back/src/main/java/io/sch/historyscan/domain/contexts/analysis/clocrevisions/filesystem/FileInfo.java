package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;

public record FileInfo(String name, String path, boolean isFile, long currentNbLines) implements Comparable<FileInfo> {
    public List<String> pathParts(String rootFolder) {
        List<String> parts = new ArrayList<>();
        int rootIndex = path.indexOf(rootFolder);
        if (rootIndex != -1) {
            String beforeRoot = path.substring(0, rootIndex + rootFolder.length());
            if (!beforeRoot.isEmpty()) {
                parts.add(beforeRoot);
            }

            String remainingPath = path.substring(rootIndex + rootFolder.length());
            if (!remainingPath.isEmpty()) {
                if (remainingPath.startsWith("/")) {
                    remainingPath = remainingPath.substring(1);
                }
                parts.addAll(Arrays.asList(remainingPath.split("/")));
            }
        } else {
            parts.addAll(Arrays.asList(path.split("/")));
        }

        return parts.stream()
                .filter(part -> !part.isEmpty())
                .toList();
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
        if(pathPart.contains("/")) {
            pathPart = "/" + pathPart;
        }
        int index = pathPart.equals(name)
                ? path.lastIndexOf(pathPart)
                : path.lastIndexOf(pathPart + "/");
        if (index == 0) {
            return null;
        }
        try {
            return path.substring(0, index - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
