package io.sch.historyscan.fake;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.FileInfo;

import static io.sch.historyscan.fake.FileNameFake.*;
import static io.sch.historyscan.fake.FilePathFake.*;

public class FileInfoFake {
    public static final FileInfo file1 = new FileInfo(fileName1, filePath1);
    public static final FileInfo file2 = new FileInfo(fileName2, filePath2);
    public static final FileInfo file3 = new FileInfo(fileName3, filePath3);
    public static final FileInfo file4 = new FileInfo(fileName4, filePath4);
    public static final FileInfo file5 = new FileInfo(fileName5, filePath5);
    public static final FileInfo file6 = new FileInfo(fileName6, filePath6);
    public static final FileInfo file7 = new FileInfo(fileName7, filePath7);
    public static final FileInfo file8 = new FileInfo(fileName8, filePath8);
    public static final FileInfo singleFile1 = new FileInfo(singleFileName1, singleFilePath1);
    public static final FileInfo singleFile2 = new FileInfo(singleFileName2, singleFilePath2);

}
