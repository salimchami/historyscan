package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import java.math.BigDecimal;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public record RevisionStats(
        long score) implements Comparable<RevisionStats> {

    public static RevisionStats of(int numberOfRevisions, int currentNbLines) {
        if (currentNbLines == 0) {
            currentNbLines = 1;
        }
        var revisionsScore = calculateRevisionsScore(numberOfRevisions, currentNbLines);
        return new RevisionStats(revisionsScore);
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
