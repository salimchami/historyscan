package io.sch.historyscan.domain.contexts.analysis.history;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.FileInfo;

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

    public String name() {
        return fileInfo.name();
    }
}
