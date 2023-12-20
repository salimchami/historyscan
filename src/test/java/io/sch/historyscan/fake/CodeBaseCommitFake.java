package io.sch.historyscan.fake;

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo;

import java.util.List;

import static io.sch.historyscan.fake.FileInfoFake.*;
import static io.sch.historyscan.fake.LocalDateTimeFake.now;

public class CodeBaseCommitFake {


    public static CodeBaseHistory defaultHistory() {
        var commitInfo = new CodeBaseHistoryCommitInfo("654354sdfgsdfgsdf",
                "author-1", now(), "message");
        return new CodeBaseHistory(
                List.of(
                        new CodeBaseCommit(commitInfo, List.of(
                                new CodeBaseHistoryCommitFile(singleFile1, 40, 20, 2, 0), // 880
                                new CodeBaseHistoryCommitFile(file1, 20, 1, 2, 5),
                                new CodeBaseHistoryCommitFile(file2, 1_500, 15, 30, 10),
                                new CodeBaseHistoryCommitFile(file3, 1_250, 150, 30, 10) // 237500
                        )),
                        new CodeBaseCommit(commitInfo, List.of(
                                new CodeBaseHistoryCommitFile(singleFile2, 40, 20, 2, 0), // 880
                                new CodeBaseHistoryCommitFile(file1, 20, 12, 0, 2),
                                new CodeBaseHistoryCommitFile(file2, 1_500, 150, 5, 100),
                                new CodeBaseHistoryCommitFile(file4, 150, 10, 100, 25), // 20250
                                new CodeBaseHistoryCommitFile(file5, 523, 90, 100, 32) // 116106
                        )),
                        new CodeBaseCommit(commitInfo, List.of(
                                new CodeBaseHistoryCommitFile(file6, 40, 12, 0, 10) // 880
                        )),
                        new CodeBaseCommit(commitInfo, List.of(
                                new CodeBaseHistoryCommitFile(file7, 35, 35, 0, 0),// 1225
                                new CodeBaseHistoryCommitFile(file8, 50, 50, 0, 0) // 2500
                        ))
                ));
    }
}
