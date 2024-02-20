package io.sch.historyscan.domain.contexts.analysis.clocrevisions

import java.math.BigDecimal

class RevisionScore(val score: Long) : Comparable<RevisionScore> {

    override fun compareTo(other: RevisionScore): Int {
        return Comparator.comparing(RevisionScore::score, Comparator.reverseOrder())
            .compare(this, other)
    }

    companion object {
        fun of(revisions: Int, currentNbLines: Long): RevisionScore {
            var nblines = 0
            if (currentNbLines == 0L) {
                nblines = 1
            }
            val revisionsScore = calculateRevisionsScore(revisions, nblines)
            return RevisionScore(revisionsScore)
        }

        @JvmStatic
        fun calculateRevisionsScore(numberOfRevisions: Int, nbLines: Int): Long {
            return BigDecimal.valueOf(numberOfRevisions.toLong())
                .multiply(BigDecimal(nbLines))
                .toLong()
        }
    }
}
