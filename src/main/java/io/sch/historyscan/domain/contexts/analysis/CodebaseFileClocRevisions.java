package io.sch.historyscan.domain.contexts.analysis;

import static java.util.Comparator.comparing;

public record CodebaseFileClocRevisions(String fileName, int numberOfModifs) implements Comparable<CodebaseFileClocRevisions> {
    @Override
    public int compareTo(CodebaseFileClocRevisions o) {
        return comparing(CodebaseFileClocRevisions::numberOfModifs)
                .thenComparing(CodebaseFileClocRevisions::fileName)
                .reversed()
                .compare(this, o);
    }

    public boolean ignored() {
        return EnumIgnoredCodeBaseFiles.ignoredFiles().stream().anyMatch(fileName::contains);
    }
}
