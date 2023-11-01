package io.sch.historyscan.domain.contexts.analysis.history;

public record CodeBaseHistoryCommitFile(
        String fileName,
        int nbLines,
        int nbAddedLines,
        int nbDeletedLines,
        int nbModifiedLines) {
    public int cloc() {
        return nbAddedLines + nbDeletedLines + nbModifiedLines;
    }
}
