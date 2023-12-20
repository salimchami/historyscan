package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import static java.util.Comparator.comparing;

public record FileInfo(String name,
                       String path) implements Comparable<FileInfo> {
    @Override
    public int compareTo(FileInfo o) {
        return comparing(FileInfo::path)
                .thenComparing(FileInfo::name)
                .compare(this, o);
    }
}
