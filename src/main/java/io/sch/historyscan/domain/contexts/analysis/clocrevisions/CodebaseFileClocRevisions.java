package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import static io.sch.historyscan.domain.contexts.analysis.EnumIgnoredCodeBaseFiles.ignoredFiles;
import static java.util.Comparator.comparing;

public record CodebaseFileClocRevisions(String fileName, int numberOfRevisions, int nbLines) implements Comparable<CodebaseFileClocRevisions> {
    @Override
    public int compareTo(CodebaseFileClocRevisions o) {
        return comparing(CodebaseFileClocRevisions::numberOfRevisions)
                .thenComparing(CodebaseFileClocRevisions::fileName)
                .reversed()
                .compare(this, o);
    }

    public boolean ignored() {
        return ignoredFiles().stream().anyMatch(fileName::contains);
    }
}
