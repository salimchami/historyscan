package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import org.jetbrains.annotations.NotNull;

import static java.util.Comparator.comparing;

public record FileInfo(String fileName,
                       String path) implements Comparable<FileInfo> {
    @Override
    public int compareTo(@NotNull FileInfo o) {
        return comparing(FileInfo::path)
                .compare(this, o);

    }
}
