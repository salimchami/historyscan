package io.sch.historyscan.fake

import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo
import java.util.List

object CodeBaseCommitFake {
    fun defaultHistory(): CodeBaseHistory? {
        val commitInfo = CodeBaseHistoryCommitInfo(
            "654354sdfgsdfgsdf",
            "author-1", LocalDateTimeFake.now()!!, "message"
        )
        return CodeBaseHistory(
            List.of(
                CodeBaseCommit(
                    commitInfo, List.of(
                        CodeBaseHistoryCommitFile(FileInfoFake.buildGradle!!, 20, 1, 2, 5),
                        CodeBaseHistoryCommitFile(FileInfoFake.featureAExtensions!!, 1500, 15, 30, 10),
                        CodeBaseHistoryCommitFile(FileInfoFake.featureALoading!!, 1250, 150, 30, 10)
                    )
                ),
                CodeBaseCommit(
                    commitInfo, List.of(
                        CodeBaseHistoryCommitFile(FileInfoFake.buildGradle, 20, 12, 0, 2),
                        CodeBaseHistoryCommitFile(FileInfoFake.featureAExtensionsAdapter!!, 1500, 150, 5, 100),
                        CodeBaseHistoryCommitFile(FileInfoFake.featureARefactoredLoading!!, 150, 10, 100, 25),
                        CodeBaseHistoryCommitFile(FileInfoFake.featureARefactoredExtensions!!, 523, 90, 100, 32)
                    )
                ),
                CodeBaseCommit(
                    commitInfo, List.of(
                        CodeBaseHistoryCommitFile(FileInfoFake.featureARefactoredExtensions, 523, 90, 100, 32),
                        CodeBaseHistoryCommitFile(FileInfoFake.featureAExtensionsAdapter, 1500, 150, 5, 100),
                        CodeBaseHistoryCommitFile(FileInfoFake.featureBFinallyLoading!!, 40, 12, 0, 10)
                    )
                ),
                CodeBaseCommit(
                    commitInfo, List.of(
                        CodeBaseHistoryCommitFile(FileInfoFake.featureBFinallyLoading, 40, 35, 0, 0),
                        CodeBaseHistoryCommitFile(FileInfoFake.thelastfeatureHexagonalFeature!!, 40, 40, 0, 0)
                    )
                )
            )
        )
    }
}
