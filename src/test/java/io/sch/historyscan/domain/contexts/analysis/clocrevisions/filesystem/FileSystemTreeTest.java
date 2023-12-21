package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.RevisionScore;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class FileSystemTreeTest {
    @Test
    void should_load_file_system_tree_from_disk() throws IOException {
        final RootFolder rootFolder = RootFolder.of("domain", "theglobalproject");
        var fsTree = new FileSystemTree(rootFolder);
        var codebasesResource = new ClassPathResource("codebases/theglobalproject");

        fsTree.addFileNodes(codebasesResource.getFile(), "theglobalproject");

        var expectedRoot = expectedRootFs();
        assertThat(fsTree)
                .extracting(FileSystemTree::getRoot)
                .isEqualTo(expectedRoot);
    }

    @NotNull
    private static FileSystemNode expectedRootFs() {
        var expectedRoot = new FileSystemNode("root", "/", false, new RevisionScore(0));
        var domain = new FileSystemNode("domain", "theglobalproject/src/main/java/io/mycompany/theproject/domain", false, new RevisionScore(0));
        expectedRoot.addChild("domain", domain);
        var boundedcontexts = new FileSystemNode("boundedcontexts", "theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts", false, new RevisionScore(0));
        domain.addChild("boundedcontexts", boundedcontexts);
        final FileSystemNode featureA = new FileSystemNode("featureA", "theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts/featureA", false, new RevisionScore(0));
        boundedcontexts.addChild("featureA", featureA);
        featureA.addChild("Extensions.java", new FileSystemNode("Extensions.java", "theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts/featureA/Extensions.java", true, new RevisionScore(0)));
        featureA.addChild("Loading.java", new FileSystemNode("Loading.java", "theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts/featureA/Loading.java", true, new RevisionScore(0)));

        final FileSystemNode featureB = new FileSystemNode("featureB", "theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts/featureB", false, new RevisionScore(0));
        boundedcontexts.addChild("featureB", featureB);
        featureB.addChild("FinallyLoading.java", new FileSystemNode("FinallyLoading.java", "theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts/featureB/FinallyLoading.java", true, new RevisionScore(0)));
        return expectedRoot;
    }
}