package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;

import java.util.List;
import java.util.stream.Collectors;

public class ClocRevisions {

    private final  List<CodeBaseCommit> commits;
    private final  RootFolder rootFolder;

    public ClocRevisions(List<CodeBaseCommit> commits, RootFolder rootFolder) {
        this.commits = commits;
        this.rootFolder = rootFolder;
    }

    List<List<CodebaseFileClocRevisions>> convertCommitsToRevisions() {
        var sortedRevisions = sortedRevisionsFrom();

        if (!this.rootFolder.isValid()) {
            return List.of(sortedRevisions);
        }

        var revisions = new ClusteredClocRevisions(rootFolder, sortedRevisions);
        return revisions.toClusters();
    }

    private List<CodebaseFileClocRevisions> sortedRevisionsFrom() {
        return commits.stream()
                .flatMap(codebaseFile -> codebaseFile.files().stream())
                .collect(Collectors.groupingBy(
                        CodeBaseHistoryCommitFile::name,
                        Collectors.summingInt(CodeBaseHistoryCommitFile::cloc)
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> CodebaseFileClocRevisions.of(entry, commits))
                .sorted().toList();
    }
}