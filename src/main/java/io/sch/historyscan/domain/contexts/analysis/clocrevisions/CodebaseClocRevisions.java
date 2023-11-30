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
        var revisions = revisionsFrom(commits, new RootFolder(rootFolder));
        var allRevisions = revisions.stream().flatMap(Collection::stream).toList();
        return new CodebaseClocRevisions(
                notIgnoredGrouped(revisions),
                ignored(allRevisions),
                extensionsOf(allRevisions));
    }


    private static List<List<CodebaseFileClocRevisions>> revisionsFrom(List<CodeBaseCommit> commits, RootFolder rootFolder) {
        var sortedRevisions = sortedRevisionsFrom(commits);

        if (!rootFolder.isValid()) {
            return List.of(sortedRevisions);
        }

        Map<String, List<CodebaseFileClocRevisions>> revisionsByDirectory = new HashMap<>();
        sortedRevisions.forEach(revision -> {
            String relativePath = revision.fileName().replace(rootFolder.value(), "");
            String directory = relativePath.contains("/") ? relativePath.substring(0, relativePath.lastIndexOf('/')) : "/";
            revisionsByDirectory.computeIfAbsent(directory, k -> new ArrayList<>())
                    .add(new CodebaseFileClocRevisions(relativePath, revision.numberOfRevisions(),
                            revision.nbLines(), revision.score()));
        });
        return new ArrayList<>(revisionsByDirectory.values());
    }

    private static List<CodebaseFileClocRevisions> sortedRevisionsFrom(List<CodeBaseCommit> commits) {
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
