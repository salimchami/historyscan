package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import java.util.List;

public class Extensions {
    private Extensions() {
    }

    public static List<String> extensionsOf(List<CodebaseFileClocRevisions> revisions) {
        return revisions.stream().filter(file -> !file.ignored())
                .filter(file1 -> !file1.ignored())
                .map(CodebaseFileClocRevisions::fileName)
                .map(fileName -> fileName.substring(fileName.lastIndexOf(".") + 1))
                .distinct()
                .sorted()
                .toList();
    }
}