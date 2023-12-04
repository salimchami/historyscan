package io.sch.historyscan.fake;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo;

import java.time.LocalDateTime;
import java.util.List;

public class CodeBaseCommitFake {

    public static CodeBaseHistory defaultHistory() {
        var singleFileName1 = "/boundedcontexts/single-file-1.java";
        var singleFileName2 = "/boundedcontexts/single-file-2.java";
        var fileName1 = "/boundedcontexts/featureA/file-1.java";
        var fileName2 = "/boundedcontexts/featureA/file-2.java";
        var fileName3 = "/boundedcontexts/featureA/file-3.java";
        var fileName4 = "/boundedcontexts/featureB/file-4.java";
        var fileName5 = "/boundedcontexts/featureB/file-5.java";
        var fileName6 = "/boundedcontexts/featureB/file-6.java";
        var commitInfo = new CodeBaseHistoryCommitInfo("654354sdfgsdfgsdf",
                "author-1", LocalDateTime.now(), "message");
        return new CodeBaseHistory(
                List.of(
                        new CodeBaseCommit(commitInfo, List.of(
                                new CodeBaseHistoryCommitFile(singleFileName1, 20, 20, 2, 0),
                                new CodeBaseHistoryCommitFile(fileName1, 20, 1, 2, 5),
                                new CodeBaseHistoryCommitFile(fileName2, 1500, 15, 30, 10),
                                new CodeBaseHistoryCommitFile(fileName3, 1250, 150, 30, 10)
                        )),
                        new CodeBaseCommit(commitInfo, List.of(
                                new CodeBaseHistoryCommitFile(singleFileName2, 20, 20, 2, 0),
                                new CodeBaseHistoryCommitFile(fileName1, 20, 12, 0, 2),
                                new CodeBaseHistoryCommitFile(fileName2, 1000, 150, 5, 100),
                                new CodeBaseHistoryCommitFile(fileName4, 150, 10, 100, 25),
                                new CodeBaseHistoryCommitFile(fileName5, 523, 90, 100, 32)
                        )),
                        new CodeBaseCommit(commitInfo, List.of(
                                new CodeBaseHistoryCommitFile(fileName6, 20, 12, 0, 10)
                        ))));
    }
}
