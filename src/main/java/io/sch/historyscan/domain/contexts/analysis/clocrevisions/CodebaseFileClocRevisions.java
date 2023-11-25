package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;

import java.util.List;

import static io.sch.historyscan.domain.contexts.analysis.common.EnumIgnoredCodeBaseFiles.ignoredFiles;
import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public record CodebaseFileClocRevisions(
        String fileName,
        int numberOfRevisions,
        int nbLines) implements Comparable<CodebaseFileClocRevisions> {

    public static CodebaseFileClocRevisions of(String fileName,
                                               int nbRevisions, List<CodeBaseCommit> commits) {
        return new CodebaseFileClocRevisions(fileName, nbRevisions,
                nbLines(fileName, commits));
    }

    private static int nbLines(String fileName, List<CodeBaseCommit> commits) {
        return commits.stream()
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
        return comparing(CodebaseFileClocRevisions::numberOfRevisions, reverseOrder())
                .thenComparing(CodebaseFileClocRevisions::fileName)
                .compare(this, o);
    }
}
