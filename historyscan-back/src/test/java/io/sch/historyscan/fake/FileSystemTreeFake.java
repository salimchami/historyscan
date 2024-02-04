package io.sch.historyscan.fake;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.CodeBaseFile;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemTree;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.RootFolder;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static io.sch.historyscan.fake.CodeBaseCommitFake.defaultHistory;

public final class FileSystemTreeFake {

    public static FileSystemTree create(String rootFolderName) throws IOException {
        var codebaseName = "theglobalproject";
        var rootFolder = RootFolder.of(rootFolderName, codebaseName);
        var fsTree = new FileSystemTree(rootFolder);
        var codebaseResource = new ClassPathResource("codebases/theglobalproject");
        var codebasesResource = new ClassPathResource("codebases");
        var rootFile = codebaseResource.getFile();
        fsTree.createFrom(new CodeBaseFile(rootFile, rootFolder,
                codebasesResource.getFile().getPath()));
        return fsTree
                .updateFilesScoreFrom(defaultHistory().commits())
                .then()
                .updateFoldersScore();
    }
}
