package io.sch.historyscan.fake;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo;

import java.time.LocalDateTime;
import java.util.List;

public class CodeBaseCommitFake {

    public static CodeBaseHistory defaultHistory() {
        var fileName1 = "file-1.java";
        var fileName2 = "file-2.java";
        return defaultCommit(fileName1, fileName2);
    }

    private static CodeBaseHistory defaultCommit(String fileName1, String fileName2) {
        var commitInfo = new CodeBaseHistoryCommitInfo("654354sdfgsdfgsdf",
                "author-1", LocalDateTime.now(), "message");
        return new CodeBaseHistory(
                List.of(
                        new CodeBaseCommit(commitInfo, List.of(
                                new CodeBaseHistoryCommitFile(fileName1, 20, 1, 2, 5),
                                new CodeBaseHistoryCommitFile(fileName2, 1500, 15, 30, 10)
                        )),
                        new CodeBaseCommit(commitInfo, List.of(
                                new CodeBaseHistoryCommitFile(fileName1, 20, 12, 0, 2),
                                new CodeBaseHistoryCommitFile(fileName2, 1500, 150, 100, 45)
                        )),
                        new CodeBaseCommit(commitInfo, List.of(
                                new CodeBaseHistoryCommitFile(fileName1, 20, 12, 0, 10)
                        ))));
    }
}
