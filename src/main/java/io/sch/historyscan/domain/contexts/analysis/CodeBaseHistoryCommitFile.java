package io.sch.historyscan.domain.contexts.analysis;

public record CodeBaseHistoryCommitFile(String fileName, int nbAddedLines, int nbDeletedLines, int nbModifiedLines) {
    public int cloc() {
        return nbAddedLines + nbDeletedLines + nbModifiedLines;
    }
}
