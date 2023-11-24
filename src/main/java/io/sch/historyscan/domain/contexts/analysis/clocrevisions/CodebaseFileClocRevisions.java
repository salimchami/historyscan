package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;

import static io.sch.historyscan.domain.contexts.analysis.common.EnumIgnoredCodeBaseFiles.ignoredFiles;
import static java.util.Comparator.comparing;

public record CodebaseFileClocRevisions(
        String fileName,
        int numberOfRevisions,
        int nbLines) implements Comparable<CodebaseFileClocRevisions> {

    public static CodebaseFileClocRevisions of(String fileName,
                                               int nbRevisions, CodeBaseHistory history) {
        return new CodebaseFileClocRevisions(fileName, nbRevisions,
                nbLines(fileName, history));
    }

    private static int nbLines(String fileName, CodeBaseHistory history) {
        return history.commits().stream()
                .filter(commit -> commit.files().stream().anyMatch(file -> file.name().equals(fileName)))
                .flatMapToInt(commit -> commit.files().stream()
                        .filter(file -> file.name().equals(fileName))
                        .mapToInt(CodeBaseHistoryCommitFile::currentNbLines))
                .max()
                .orElse(0);
    }

    public boolean ignored() {
        return ignoredFiles().stream().anyMatch(fileName::contains);
    }

    @Override
    public int compareTo(CodebaseFileClocRevisions o) {
        return comparing(CodebaseFileClocRevisions::numberOfRevisions)
                .thenComparing(CodebaseFileClocRevisions::fileName)
                .reversed()
                .compare(this, o);
    }
}
