package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import java.math.BigDecimal;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public record RevisionScore(
        long score) implements Comparable<RevisionScore> {

    public static RevisionScore of(int revisions, long currentNbLines) {
        if (currentNbLines == 0) {
            currentNbLines = 1;
        }
        var revisionsScore = calculateRevisionsScore(revisions, currentNbLines);
        return new RevisionScore(revisionsScore);
    }

    static long calculateRevisionsScore(int numberOfRevisions, long nbLines) {
        return BigDecimal.valueOf(numberOfRevisions)
                .multiply(BigDecimal.valueOf(nbLines))
                .longValue();
    }

    @Override
    public int compareTo(RevisionScore o) {
        return comparing(RevisionScore::score, reverseOrder())
                .compare(this, o);
    }
}
