package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;

import java.util.List;
import java.util.Map;

import static io.sch.historyscan.domain.contexts.analysis.common.EnumIgnoredCodeBaseFiles.ignoredFiles;
import static java.util.Comparator.comparing;

public record ClocRevisionsFile(
        FileInfo file,
        RevisionStats stats) implements Comparable<ClocRevisionsFile> {

    public static ClocRevisionsFile of(Map.Entry<FileInfo, Integer> entry, List<CodeBaseCommit> commits) {
        var fileName = entry.getKey();
        var nbRevisions = entry.getValue();
        var nbLines = nbLines(fileName.fileName(), commits);
        return new ClocRevisionsFile(
                new FileInfo(fileName.fileName(), fileName.path()),
                RevisionStats.of(nbRevisions, nbLines));
    }

    private static int nbLines(String fileName, List<CodeBaseCommit> commits) {
        return commits.stream()
                .filter(commit -> commit.files().stream().anyMatch(file -> file.fileInfo().fileName().equals(fileName)))
                .flatMapToInt(commit -> commit.files().stream()
                        .filter(file -> file.fileInfo().fileName().equals(fileName))
                        .mapToInt(CodeBaseHistoryCommitFile::currentNbLines))
                .max()
                .orElse(0);
    }

    public boolean ignored() {
        return ignoredFiles().stream().anyMatch(ignoredFile -> file.fileName().contains(ignoredFile));
    }

    @Override
    public int compareTo(ClocRevisionsFile o) {
        return comparing(ClocRevisionsFile::stats)
                .thenComparing(ClocRevisionsFile::file)
                .compare(this, o);
    }

    public String fileName() {
        return file.fileName();
    }

    public String filePath() {
        return file.path();
    }
}
