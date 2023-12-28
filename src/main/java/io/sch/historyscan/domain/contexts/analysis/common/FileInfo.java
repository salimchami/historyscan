package io.sch.historyscan.domain.contexts.analysis.common;

import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparing;

public record FileInfo(String name,
                       String path, boolean isFile) implements Comparable<FileInfo> {
    @Override
    public int compareTo(FileInfo o) {
        return comparing(FileInfo::path)
                .thenComparing(FileInfo::name)
                .compare(this, o);
    }

    public List<String> pathParts() {
        return Arrays.stream(path.trim().split("/")).filter(part -> !part.isEmpty()).toList();
    }
}
