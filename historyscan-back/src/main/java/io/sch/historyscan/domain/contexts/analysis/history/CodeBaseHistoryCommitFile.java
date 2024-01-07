package io.sch.historyscan.domain.contexts.analysis.history;

public record CodeBaseHistoryCommitFile(
        FileInfo fileInfo,
        int currentNbLines,
        int nbAddedLines,
        int nbDeletedLines,
        int nbModifiedLines) {

    public int cloc() {
        return nbAddedLines + nbDeletedLines + nbModifiedLines;
    }

    public String path() {
        return fileInfo.path();
    }
}
