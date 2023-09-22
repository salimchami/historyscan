package io.sch.historyscan.domain.contexts.analysis;

public record CodeBaseHistoryCommitFile(String fileName, int nbLinesAdded, int nbLinesDeleted) {
}
