package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;

import java.util.List;
import java.util.stream.Collectors;

public class ClocRevisions {

    private final  List<CodeBaseCommit> commits;

    public ClocRevisions(List<CodeBaseCommit> commits) {
        this.commits = commits;
    }

    List<ClocRevisionsFileCluster> convertCommitsToRevisions() {
        var sortedRevisions = sortedRevisionsFrom();
        var revisions = new ClusteredClocRevisions(sortedRevisions);
        return revisions.toClusters();
    }

    private List<ClocRevisionsFile> sortedRevisionsFrom() {
        return commits.stream()
                .flatMap(codebaseFile -> codebaseFile.files().stream())
                .collect(Collectors.groupingBy(
                        CodeBaseHistoryCommitFile::fileInfo,
                        Collectors.summingInt(CodeBaseHistoryCommitFile::cloc)
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> ClocRevisionsFile.of(entry, commits))
                .sorted()
                .toList();
    }
}