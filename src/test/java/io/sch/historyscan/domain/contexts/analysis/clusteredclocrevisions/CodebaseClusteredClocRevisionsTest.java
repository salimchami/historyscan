package io.sch.historyscan.domain.contexts.analysis.clusteredclocrevisions;

import io.sch.historyscan.domain.contexts.analysis.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CodebaseClusteredClocRevisionsTest {

    @Test
    void should_generate_clustered_cloc_revisions() {
        var fileName0 = "file-0.java";
        var fileName1 = "file-1.java";
        var fileName2 = "file-2.java";
        var fileName3 = "file-3.java";
        var fileName4 = "file-4.java";
        var fileName5 = "file-5.png";
        var fileName6 = "file-4.java";
        var fileName7 = "file-4.java";
        var date = LocalDateTime.now();

        CodebaseClusteredClocRevisions.of(List.of(
                        new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-0", "author-0", date, "message 0"), List.of(
                                new CodeBaseHistoryCommitFile(fileName0, 100, 1, 2, 5)
                        )),
                        new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-0", "author-0", date, "message 0"), List.of(
                                new CodeBaseHistoryCommitFile(fileName0, 100, 1, 2, 5),
                                new CodeBaseHistoryCommitFile(fileName6, 100, 1, 2, 5)
                        )),
                        new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-0", "author-0", date, "message 0"), List.of(
                                new CodeBaseHistoryCommitFile(fileName6, 100, 1, 2, 5),
                                new CodeBaseHistoryCommitFile(fileName7, 100, 1, 2, 5)
                        )),
                        new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-1", "author-1", date, "message 1"), List.of(
                                new CodeBaseHistoryCommitFile(fileName1, 100, 1, 2, 5),
                                new CodeBaseHistoryCommitFile(fileName2, 100, 15, 30, 10)
                        )),
                        new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-1-bis", "author-1", date, "message 1 bis"), List.of(
                                new CodeBaseHistoryCommitFile(fileName1, 100, 1, 2, 5),
                                new CodeBaseHistoryCommitFile(fileName2, 100, 15, 30, 10)
                        )),
                        new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-2", "author-1", date, "message 2"), List.of(
                                new CodeBaseHistoryCommitFile(fileName1, 100, 12, 0, 2),
                                new CodeBaseHistoryCommitFile(fileName3, 100, 150, 100, 45)
                        )),
                        new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-3", "author-1", date, "message 3"), List.of(
                                new CodeBaseHistoryCommitFile(fileName2, 100, 12, 0, 10),
                                new CodeBaseHistoryCommitFile(fileName4, 100, 12, 0, 10)
                        )),
                        new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-4", "author-1", date, "message 4"), List.of(
                                new CodeBaseHistoryCommitFile(fileName3, 100, 12, 0, 10),
                                new CodeBaseHistoryCommitFile(fileName4, 100, 12, 0, 10),
                                new CodeBaseHistoryCommitFile(fileName5, 100, 12, 0, 10)
                        ))
                ),
                new CodebaseClocRevisions(List.of(), List.of(), List.of("java")));
    }
}