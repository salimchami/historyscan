package io.sch.historyscan.fake;

import io.sch.historyscan.domain.contexts.analysis.history.FileInfo;

import static io.sch.historyscan.fake.FileNameFake.thelastfeatureHexagonalFeatureFileName;
import static io.sch.historyscan.fake.FileNameFake.*;
import static io.sch.historyscan.fake.FilePathFake.*;

public class FileInfoFake {
    public static final FileInfo buildGradle = new FileInfo(buildGradleFileName, buildGradleFilePath, true);
    public static final FileInfo featureARefactoredExtensions = new FileInfo(featureARefactoredExtensionsFileName, featureARefactoredExtensionsFilePath, true);
    public static final FileInfo featureARefactoredLoading = new FileInfo(featureARefactoredLoadingFileName, featureARefactoredLoadingFilePath, true);
    public static final FileInfo featureAExtensions = new FileInfo(featureAExtensionsFileName, featureAExtensionsFilePath, true);
    public static final FileInfo featureALoading = new FileInfo(featureALoadingFileName, featureALoadingFilePath, true);
    public static final FileInfo featureBFinallyLoading = new FileInfo(featureBFinallyLoadingFileName, featureBFinallyLoadingFilePath, true);
    public static final FileInfo featureAExtensionsAdapter = new FileInfo(featureAExtensionsAdapterFileName, featureAExtensionsAdapterFilePath, true);
    public static final FileInfo thelastfeatureHexagonalFeature = new FileInfo(thelastfeatureHexagonalFeatureFileName, thelastfeatureHexagonalFeatureFilePath, true);
}
