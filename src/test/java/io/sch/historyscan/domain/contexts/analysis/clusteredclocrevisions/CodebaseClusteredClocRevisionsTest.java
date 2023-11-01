package io.sch.historyscan.domain.contexts.analysis.clusteredclocrevisions;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseFileClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CodebaseClusteredClocRevisionsTest {

    private LocalDateTime date;
    private String fileName0;
    private String fileName6;
    private String fileName7;
    private String fileName1;
    private String fileName2;
    private String fileName3;
    private String fileName4;
    private String fileName5;

    @BeforeEach
    void initTest() {
        fileName0 = "file-0.java";
        fileName1 = "file-1.java";
        fileName2 = "file-2.java";
        fileName3 = "file-3.java";
        fileName4 = "file-4.java";
        fileName5 = "file-5.png";
        fileName6 = "file-6.java";
        fileName7 = "file-7.java";
        date = LocalDateTime.now();
    }

    @Test
    void should_generate_clustered_cloc_revisions() {
        final CodebaseClocRevisions revisions = new CodebaseClocRevisions(List.of(), List.of(), List.of("java"));
        final List<CodeBaseCommit> commits = initCommits();
        var clusteredClocRevisions = CodebaseClusteredClocRevisions.of(commits, revisions);
        assertThat(clusteredClocRevisions).isEqualTo(expectedClusteredClocRevisions());
    }

    private CodebaseClusteredClocRevisions expectedClusteredClocRevisions() {
        List<List<CodebaseFileClocRevisions>> revisions = List.of(
                List.of(
                        new CodebaseFileClocRevisions(fileName6, 17, 105),
                        new CodebaseFileClocRevisions(fileName0, 16, 100),
                        new CodebaseFileClocRevisions(fileName7, 8, 100)
                ),
                List.of(
                        new CodebaseFileClocRevisions(fileName3, 317, 100),
                        new CodebaseFileClocRevisions(fileName2, 132, 105),
                        new CodebaseFileClocRevisions(fileName4, 44, 100),
                        new CodebaseFileClocRevisions(fileName1, 30, 100),
                        new CodebaseFileClocRevisions(fileName5, 22, 100)
                )
        );
        return new CodebaseClusteredClocRevisions(revisions, List.of(), List.of("java"));
    }

    private List<CodeBaseCommit> initCommits() {
        return List.of(
                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-0", "author-0", date, "message 0"), List.of(
                        new CodeBaseHistoryCommitFile(fileName0, 100, 1, 2, 5)
                )),
                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-0", "author-0", date, "message 0"), List.of(
                        new CodeBaseHistoryCommitFile(fileName0, 100, 1, 2, 5),
                        new CodeBaseHistoryCommitFile(fileName6, 105, 1, 3, 5)
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
        );
    }
}