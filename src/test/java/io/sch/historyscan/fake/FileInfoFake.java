package io.sch.historyscan.fake;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.FileInfo;

import static io.sch.historyscan.fake.FileNameFake.*;
import static io.sch.historyscan.fake.FilePathFake.*;

public class FileInfoFake {
    public static final FileInfo buildGradle = new FileInfo(buildGradleFileName, buildGradleFilePath);
    public static final FileInfo featureARefactoredExtensions = new FileInfo(featureARefactoredExtensionsFileName, featureARefactoredExtensionsFilePath);
    public static final FileInfo featureARefactoredLoading = new FileInfo(featureARefactoredLoadingFileName, featureARefactoredLoadingFilePath);
    public static final FileInfo featureAExtensions = new FileInfo(featureAExtensionsFileName, featureAExtensionsFilePath);
    public static final FileInfo featureALoading = new FileInfo(featureALoadingFileName, featureALoadingFilePath);
    public static final FileInfo featureBFinallyLoading = new FileInfo(featureBFinallyLoadingFileName, featureBFinallyLoadingFilePath);
    public static final FileInfo featureAExtensionsAdapter = new FileInfo(featureAExtensionsAdapterFileName, featureAExtensionsAdapterFilePath);
}
