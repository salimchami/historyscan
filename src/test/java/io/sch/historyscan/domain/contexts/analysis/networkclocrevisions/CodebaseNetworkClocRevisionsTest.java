package io.sch.historyscan.domain.contexts.analysis.networkclocrevisions;

import io.sch.historyscan.domain.contexts.analysis.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.Weight;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseFileClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CodebaseNetworkClocRevisionsTest {

    @Test
    void name() {
        var fileName0 = "file-0.java";
        var fileName1 = "file-1.java";
        var fileName2 = "file-2.java";
        var fileName3 = "file-3.java";
        var fileName4 = "file-4.java";
        var fileName5 = "file-5.png";
        var date = LocalDateTime.now();
        var network = CodebaseNetworkClocRevisions.of(List.of(
                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-0", "author-0", date, "message 0"), List.of(
                        new CodeBaseHistoryCommitFile(fileName0, 1, 2, 5)
                )),
                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-1", "author-1", date, "message 1"), List.of(
                        new CodeBaseHistoryCommitFile(fileName1, 1, 2, 5),
                        new CodeBaseHistoryCommitFile(fileName2, 15, 30, 10)
                )),
                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-1-bis", "author-1", date, "message 1 bis"), List.of(
                        new CodeBaseHistoryCommitFile(fileName1, 1, 2, 5),
                        new CodeBaseHistoryCommitFile(fileName2, 15, 30, 10)
                )),
                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-2", "author-1", date, "message 2"), List.of(
                        new CodeBaseHistoryCommitFile(fileName1, 12, 0, 2),
                        new CodeBaseHistoryCommitFile(fileName3, 150, 100, 45)
                )),
                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-3", "author-1", date, "message 3"), List.of(
                        new CodeBaseHistoryCommitFile(fileName2, 12, 0, 10),
                        new CodeBaseHistoryCommitFile(fileName4, 12, 0, 10)
                )),
                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-4", "author-1", date, "message 4"), List.of(
                        new CodeBaseHistoryCommitFile(fileName3, 12, 0, 10),
                        new CodeBaseHistoryCommitFile(fileName4, 12, 0, 10),
                        new CodeBaseHistoryCommitFile(fileName5, 12, 0, 10)
                ))
        ), CodebaseClocRevisions.of(List.of(
                new CodebaseFileClocRevisions(fileName1, 44),
                new CodebaseFileClocRevisions(fileName2, 350)
        )));

        assertThat(network.extensions()).isEqualTo(List.of("java"));
        assertThat(network.ignoredRevisions()).isEqualTo(List.of(new CodebaseFileClocRevisions(fileName5, 22)));
        assertThat(network.revisions().get(new CodebaseFileClocRevisions(fileName0, 8))).isEmpty();
        assertThat(network.revisions().get(new CodebaseFileClocRevisions(fileName1, 30))).isEqualTo(Map.of(
                new FileName(fileName2), new Weight(2),
                new FileName(fileName3), new Weight(1)
        ));
        assertThat(network.revisions().get(new CodebaseFileClocRevisions(fileName2, 132))).isEqualTo(Map.of(
                new FileName(fileName1), new Weight(2),
                new FileName(fileName4), new Weight(1)
        ));
        assertThat(network.revisions().get(new CodebaseFileClocRevisions(fileName3, 317))).isEqualTo(Map.of(
                new FileName(fileName1), new Weight(1),
                new FileName(fileName4), new Weight(1),
                new FileName(fileName5), new Weight(1)
        ));
        assertThat(network.revisions().get(new CodebaseFileClocRevisions(fileName4, 44))).isEqualTo(Map.of(
                new FileName(fileName2), new Weight(1),
                new FileName(fileName3), new Weight(1),
                new FileName(fileName5), new Weight(1)
        ));

        //FIXME
    }
}