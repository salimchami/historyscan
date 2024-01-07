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
                                new CodeBaseHistoryCommitFile(buildGradle, 20, 1, 2, 5),
                                new CodeBaseHistoryCommitFile(featureAExtensions, 1_500, 15, 30, 10),
                                new CodeBaseHistoryCommitFile(featureALoading, 1_250, 150, 30, 10)
                        )),
                        new CodeBaseCommit(commitInfo, List.of(
                                new CodeBaseHistoryCommitFile(buildGradle, 20, 12, 0, 2),
                                new CodeBaseHistoryCommitFile(featureAExtensionsAdapter, 1_500, 150, 5, 100),
                                new CodeBaseHistoryCommitFile(featureARefactoredLoading, 150, 10, 100, 25),
                                new CodeBaseHistoryCommitFile(featureARefactoredExtensions, 523, 90, 100, 32)
                        )),
                        new CodeBaseCommit(commitInfo, List.of(
                                new CodeBaseHistoryCommitFile(featureBFinallyLoading, 40, 12, 0, 10)
                        )),
                        new CodeBaseCommit(commitInfo, List.of(
                                new CodeBaseHistoryCommitFile(featureBFinallyLoading, 40, 35, 0, 0)
                        ))
                ));
    }
}
