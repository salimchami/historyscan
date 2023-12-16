package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import java.math.BigDecimal;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public record RevisionStats(
        int numberOfRevisions,
        int nbLines,
        long score) implements Comparable<RevisionStats> {

    public static RevisionStats of(int numberOfRevisions, int nbLines) {
        if (nbLines == 0) {
            nbLines = 1;
        }
        var revisionsScore = calculateRevisionsScore(numberOfRevisions, nbLines);
        return new RevisionStats(numberOfRevisions, nbLines, revisionsScore);
    }

    static long calculateRevisionsScore(int numberOfRevisions, int nbLines) {
        return BigDecimal.valueOf(numberOfRevisions)
                .multiply(BigDecimal.valueOf(nbLines))
                .longValue();
    }

    @Override
    public int compareTo(RevisionStats o) {
        return comparing(RevisionStats::score, reverseOrder())
                .compare(this, o);
    }
}
