package io.sch.historyscan.domain.contexts.analysis;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CodeBaseHistoryTest {

    @Test
    void should_convert_history_to_cloc_and_revisions() {
        var fileName1 = "file-1.java";
        var fileName2 = "file-2.java";
        var date = LocalDateTime.now();
        var codeBaseHistory = new CodeBaseHistory(List.of(
                new CodeBaseFile(new CodeBaseHistoryCommitInfo("commit-1", "author-1", date, "message 1"), List.of(
                        new CodeBaseHistoryCommitFile(fileName1, 1, 2),
                        new CodeBaseHistoryCommitFile(fileName2, 15, 30)
                )),
                new CodeBaseFile(new CodeBaseHistoryCommitInfo("commit-2", "author-1", date, "message 2"), List.of(
                        new CodeBaseHistoryCommitFile(fileName1, 12, 0),
                        new CodeBaseHistoryCommitFile(fileName2, 150, 100)
                )),
                new CodeBaseFile(new CodeBaseHistoryCommitInfo("commit-3", "author-1", date, "message 3"), List.of(
                        new CodeBaseHistoryCommitFile(fileName1, 12, 0)
                ))
        ));
        var clocRevisions = codeBaseHistory.toClocRevisions();
        assertThat(clocRevisions).isEqualTo(new CodebaseClocRevisions(List.of(
                new CodebaseFileClocRevisions(fileName1, 3),
                new CodebaseFileClocRevisions(fileName2, 2)
        )));
    }
}