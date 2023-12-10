package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import static io.sch.historyscan.domain.contexts.analysis.common.EnumIgnoredCodeBaseFiles.ignoredFiles;
import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public record CodebaseFileClocRevisions(
        String fileName,
        int numberOfRevisions,
        int nbLines, int score) implements Comparable<CodebaseFileClocRevisions> {

    public static CodebaseFileClocRevisions of(Map.Entry<String, Integer> entry, List<CodeBaseCommit> commits) {
        var fileName = entry.getKey();
        var nbRevisions = entry.getValue();
        var nbLines = nbLines(fileName, commits);
        var score = scoreFrom(nbRevisions, nbLines);
        return new CodebaseFileClocRevisions(fileName, nbRevisions, nbLines, score);
    }

    private static int scoreFrom(int nbRevisions, int nbLines) {
        if (nbLines == 0) {
            nbLines = 1;
        }
        return BigDecimal.valueOf(nbRevisions)
                .divide(BigDecimal.valueOf(nbLines), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).intValue();
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
        return comparing(CodebaseFileClocRevisions::score, reverseOrder())
                .thenComparing(CodebaseFileClocRevisions::fileName)
                .compare(this, o);
    }
}
