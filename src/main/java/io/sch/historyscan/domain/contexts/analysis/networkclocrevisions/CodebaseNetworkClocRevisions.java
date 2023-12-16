package io.sch.historyscan.domain.contexts.analysis.networkclocrevisions;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.ClocRevisionsFile;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.FileInfo;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record CodebaseNetworkClocRevisions(
        Map<ClocRevisionsFile, Map<FileInfo, Weight>> revisions,
        List<ClocRevisionsFile> ignoredRevisions,
        List<String> extensions) {

    public CodebaseNetworkClocRevisions {
        revisions = Map.copyOf(revisions);
        ignoredRevisions = List.copyOf(ignoredRevisions);
        extensions = List.copyOf(extensions);
    }
    public static CodebaseNetworkClocRevisions of(List<CodeBaseCommit> commits, CodebaseClocRevisions revisionsList) {
        return new CodebaseNetworkClocRevisions(
                linksMap(revisionsList.revisions(), commits),
                revisionsList.ignoredRevisions(),
                revisionsList.extensions());
    }

    private static Map<ClocRevisionsFile, Map<FileInfo, Weight>> linksMap(List<List<ClocRevisionsFile>> revisions, List<CodeBaseCommit> commits) {
        var map = new HashMap<ClocRevisionsFile, Map<FileInfo, Weight>>();
//        for (CodebaseFileClocRevisions revision : revisions) {
//            var fileMap = new HashMap<FileName, Weight>();
//            for (CodeBaseCommit commit : commits) {
//                commit.files().stream()
//                        .filter(codeBaseHistoryCommitFile -> codeBaseHistoryCommitFile.name().equals(revision.fileName()))
//                        .findFirst()
//                        .ifPresent(revisionFileCommit -> commit.files().forEach(fileCommit -> {
//                            if (!fileCommit.name().equals(revision.fileName())) {
//                                var linkedFileName = new FileName(fileCommit.name());
//                                var weight = fileMap.getOrDefault(linkedFileName, Weight.ZERO);
//                                fileMap.put(linkedFileName, new Weight(weight.value() + 1));
//                            }
//                        }));
//            }
//            map.put(revision, fileMap);
//        }
        return map;
    }
}
