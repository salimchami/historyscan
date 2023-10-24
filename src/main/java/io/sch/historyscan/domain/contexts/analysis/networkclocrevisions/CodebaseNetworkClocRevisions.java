package io.sch.historyscan.domain.contexts.analysis.networkclocrevisions;

import io.sch.historyscan.domain.contexts.analysis.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.Weight;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseFileClocRevisions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record CodebaseNetworkClocRevisions(
        Map<CodebaseFileClocRevisions, Map<FileName, Weight>> revisions,
        List<CodebaseFileClocRevisions> ignoredRevisions,
        List<String> extensions) {
    public static CodebaseNetworkClocRevisions of(List<CodeBaseCommit> commits, CodebaseClocRevisions revisionsList) {
        return new CodebaseNetworkClocRevisions(
                linksMap(revisionsList.revisions(), commits),
                revisionsList.ignoredRevisions(),
                revisionsList.extensions());
    }

    private static Map<CodebaseFileClocRevisions, Map<FileName, Weight>> linksMap(List<CodebaseFileClocRevisions> revisions, List<CodeBaseCommit> commits) {
        var map = new HashMap<CodebaseFileClocRevisions, Map<FileName, Weight>>();
        for (CodebaseFileClocRevisions revision : revisions) {
            var fileMap = new HashMap<FileName, Weight>();
            for (CodeBaseCommit commit : commits) {
                commit.files().stream()
                        .filter(codeBaseHistoryCommitFile -> codeBaseHistoryCommitFile.fileName().equals(revision.fileName()))
                        .findFirst()
                        .ifPresent(revisionFileCommit -> commit.files().forEach(fileCommit -> {
                            if (!fileCommit.fileName().equals(revision.fileName())) {
                                var linkedFileName = new FileName(fileCommit.fileName());
                                var weight = fileMap.getOrDefault(linkedFileName, Weight.ZERO);
                                fileMap.put(linkedFileName, new Weight(weight.value() + 1));
                            }
                        }));
            }
            map.put(revision, fileMap);
        }
        return map;
    }
}
