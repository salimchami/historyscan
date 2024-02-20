package io.sch.historyscan.fake

import io.sch.historyscan.domain.contexts.analysis.history.FileInfo

object FileInfoFake {
    val buildGradle: FileInfo? = FileInfo(
        FileNameFake.buildGradleFileName!!, FilePathFake.buildGradleFilePath!!, true
    )
    val featureARefactoredExtensions: FileInfo? = FileInfo(
        FileNameFake.featureARefactoredExtensionsFileName!!, FilePathFake.featureARefactoredExtensionsFilePath!!, true
    )
    val featureARefactoredLoading: FileInfo? = FileInfo(
        FileNameFake.featureARefactoredLoadingFileName!!, FilePathFake.featureARefactoredLoadingFilePath!!, true
    )
    val featureAExtensions: FileInfo? = FileInfo(
        FileNameFake.featureAExtensionsFileName!!, FilePathFake.featureAExtensionsFilePath!!, true
    )
    val featureALoading: FileInfo? = FileInfo(
        FileNameFake.featureALoadingFileName!!, FilePathFake.featureALoadingFilePath!!, true
    )
    val featureBFinallyLoading: FileInfo? = FileInfo(
        FileNameFake.featureBFinallyLoadingFileName!!, FilePathFake.featureBFinallyLoadingFilePath!!, true
    )
    val featureAExtensionsAdapter: FileInfo? = FileInfo(
        FileNameFake.featureAExtensionsAdapterFileName!!, FilePathFake.featureAExtensionsAdapterFilePath!!, true
    )
    val thelastfeatureHexagonalFeature: FileInfo? = FileInfo(
        FileNameFake.thelastfeatureHexagonalFeatureFileName!!,
        FilePathFake.thelastfeatureHexagonalFeatureFilePath!!,
        true
    )
}
