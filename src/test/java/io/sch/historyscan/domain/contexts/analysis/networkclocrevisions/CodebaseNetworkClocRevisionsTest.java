package io.sch.historyscan.domain.contexts.analysis.networkclocrevisions;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
//        var network = CodebaseNetworkClocRevisions.of(List.of(
//                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-0", "author-0", date, "message 0"), List.of(
//                        new CodeBaseHistoryCommitFile(fileName0, 100, 1, 2, 5)
//                )),
//                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-1", "author-1", date, "message 1"), List.of(
//                        new CodeBaseHistoryCommitFile(fileName1, 100, 1, 2, 5),
//                        new CodeBaseHistoryCommitFile(fileName2, 100, 15, 30, 10)
//                )),
//                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-1-bis", "author-1", date, "message 1 bis"), List.of(
//                        new CodeBaseHistoryCommitFile(fileName1, 100, 1, 2, 5),
//                        new CodeBaseHistoryCommitFile(fileName2, 100, 15, 30, 10)
//                )),
//                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-2", "author-1", date, "message 2"), List.of(
//                        new CodeBaseHistoryCommitFile(fileName1, 100, 12, 0, 2),
//                        new CodeBaseHistoryCommitFile(fileName3, 100, 150, 100, 45)
//                )),
//                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-3", "author-1", date, "message 3"), List.of(
//                        new CodeBaseHistoryCommitFile(fileName2, 100, 12, 0, 10),
//                        new CodeBaseHistoryCommitFile(fileName4, 100, 12, 0, 10)
//                )),
//                new CodeBaseCommit(new CodeBaseHistoryCommitInfo("commit-4", "author-1", date, "message 4"), List.of(
//                        new CodeBaseHistoryCommitFile(fileName3, 100, 12, 0, 10),
//                        new CodeBaseHistoryCommitFile(fileName4, 100, 12, 0, 10),
//                        new CodeBaseHistoryCommitFile(fileName5, 100, 12, 0, 10)
//                ))
//        ), null);
//
//        assertThat(network.extensions()).isEqualTo(List.of("java"));
//        assertThat(network.ignoredRevisions()).isEqualTo(List.of(new CodebaseFileClocRevisions(fileName5, 22, 100, 10)));
//        assertThat(network.revisions().get(new CodebaseFileClocRevisions(fileName0, 8, 100, 10))).isEmpty();
//        assertThat(network.revisions().get(new CodebaseFileClocRevisions(fileName1, 30, 100, 10))).isEqualTo(Map.of(
//                new FileInfo(fileName2), new Weight(2),
//                new FileInfo(fileName3), new Weight(1)
//        ));
//        assertThat(network.revisions().get(new CodebaseFileClocRevisions(fileName2, 132, 100, 10))).isEqualTo(Map.of(
//                new FileInfo(fileName1), new Weight(2),
//                new FileInfo(fileName4), new Weight(1)
//        ));
//        assertThat(network.revisions().get(new CodebaseFileClocRevisions(fileName3, 317, 100, 10))).isEqualTo(Map.of(
//                new FileInfo(fileName1), new Weight(1),
//                new FileInfo(fileName4), new Weight(1),
//                new FileInfo(fileName5), new Weight(1)
//        ));
//        assertThat(network.revisions().get(new CodebaseFileClocRevisions(fileName4, 44, 100, 10))).isEqualTo(Map.of(
//                new FileInfo(fileName2), new Weight(1),
//                new FileInfo(fileName3), new Weight(1),
//                new FileInfo(fileName5), new Weight(1)
//        ));

        //FIXME
    }
}