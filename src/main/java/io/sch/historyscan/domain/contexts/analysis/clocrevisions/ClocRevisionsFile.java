package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;

import java.util.List;
import java.util.Map;

import static io.sch.historyscan.domain.contexts.analysis.common.EnumIgnoredCodeBaseFiles.ignoredFiles;
import static java.util.Comparator.comparing;

public record ClocRevisionsFile(
        FileInfo file,
        RevisionScore revisionScore) implements Comparable<ClocRevisionsFile> {

    public static ClocRevisionsFile of(Map.Entry<FileInfo, Integer> entry, List<CodeBaseCommit> commits) {
        var fileName = entry.getKey();
        var nbRevisions = entry.getValue();
        var nbLines = nbLines(fileName.path(), commits);
        return new ClocRevisionsFile(
                new FileInfo(fileName.name(), fileName.path()),
                RevisionScore.of(nbRevisions, nbLines));
    }

    private static int nbLines(String filePath, List<CodeBaseCommit> commits) {
        return commits.stream()
                .filter(commit -> commit.files().stream().anyMatch(file -> file.fileInfo().path().equals(filePath)))
                .flatMapToInt(commit -> commit.files().stream()
                        .filter(file -> file.fileInfo().path().equals(filePath))
                        .mapToInt(CodeBaseHistoryCommitFile::currentNbLines))
                .max()
                .orElse(0);
    }

    public boolean ignored() {
        return ignoredFiles().stream().anyMatch(ignoredFile -> file.name().contains(ignoredFile));
    }

    @Override
    public int compareTo(ClocRevisionsFile o) {
        return comparing(ClocRevisionsFile::revisionScore)
                .thenComparing(ClocRevisionsFile::filePath)
                .compare(this, o);
    }

    public String fileName() {
        return file.name();
    }

    public String filePath() {
        return file.path();
    }

    public String pathFrom(String part) {
        int index = filePath().indexOf(part);
        if (index != -1) {
            return filePath().substring(0, index + part.length());
        }
        return null;
    }
}
