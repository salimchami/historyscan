package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDValueObject;

import java.util.*;
import java.util.stream.Collectors;

import static io.sch.historyscan.domain.contexts.analysis.clocrevisions.Extensions.extensionsOf;
import static io.sch.historyscan.domain.contexts.analysis.clocrevisions.IgnoredRevisions.ignored;
import static io.sch.historyscan.domain.contexts.analysis.clocrevisions.IgnoredRevisions.notIgnoredGrouped;

@DDDValueObject
public record CodebaseClocRevisions(
        List<List<CodebaseFileClocRevisions>> revisions,
        List<CodebaseFileClocRevisions> ignoredRevisions,
        List<String> extensions) {
    public CodebaseClocRevisions {
        revisions = List.copyOf(revisions);
        ignoredRevisions = List.copyOf(ignoredRevisions);
        extensions = List.copyOf(extensions);
    }

    public static CodebaseClocRevisions of(List<CodeBaseCommit> commits) {
        return CodebaseClocRevisions.of(commits, "");
    }

    public static CodebaseClocRevisions of(List<CodeBaseCommit> commits, String rootFolder) {
        var revisions = revisionsFrom(commits, rootFolder);
        var allRevisions = revisions.stream().flatMap(Collection::stream).toList();
        return new CodebaseClocRevisions(
                notIgnoredGrouped(revisions),
                ignored(allRevisions),
                extensionsOf(allRevisions));
    }



    private static List<List<CodebaseFileClocRevisions>> revisionsFrom(List<CodeBaseCommit> commits, String rootFolder) {
        final List<CodebaseFileClocRevisions> sorted = commits.stream()
                .flatMap(codebaseFile -> codebaseFile.files().stream())
                .collect(Collectors.groupingBy(
                        CodeBaseHistoryCommitFile::name,
                        Collectors.summingInt(CodeBaseHistoryCommitFile::cloc)
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> CodebaseFileClocRevisions.of(entry, commits))
                .sorted().toList();

        if( rootFolder.isEmpty()) {
            return List.of(sorted);
        }
        Map<String, List<CodebaseFileClocRevisions>> map = new HashMap<>();
        for (var revision : sorted) {
            String relativePath = revision.fileName().replace(rootFolder, "");

            String directory = relativePath.contains("/") ? relativePath.substring(0, relativePath.lastIndexOf('/')) : "/";

            map.computeIfAbsent(directory, k -> new ArrayList<>())
                    .add(new CodebaseFileClocRevisions(relativePath, revision.numberOfRevisions(),
                            revision.nbLines(), revision.score()));
        }

        return new ArrayList<>(map.values());
    }

}
