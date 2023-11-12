package io.sch.historyscan.domain.contexts.analysis.clusteredclocrevisions;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseFileClocRevisions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CodebaseClusteredClocRevisionsTest {

    private String fileName0;
    private String fileName6;
    private String fileName7;
    private String fileName1;
    private String fileName2;
    private String fileName3;
    private String fileName4;
    private String fileName5;

    @BeforeEach
    void initTest() {
        fileName0 = "/features/featureA/file-0.java";
        fileName1 = "/features/featureB/file-1.java";
        fileName2 = "/features/featureB/file-2.java";
        fileName3 = "/features/featureB/file-3.java";
        fileName4 = "/features/featureB/file-4.java";
        fileName5 = "/features/featureB/file-5.png";
        fileName6 = "/features/featureA/file-6.java";
        fileName7 = "/features/featureA/file-7.java";
    }

    @Test
    void should_generate_clustered_cloc_revisions() {
        final CodebaseClocRevisions revisions = new CodebaseClocRevisions(List.of(
                new CodebaseFileClocRevisions(fileName6, 17, 105),
                new CodebaseFileClocRevisions(fileName0, 16, 100),
                new CodebaseFileClocRevisions(fileName7, 8, 100),
                new CodebaseFileClocRevisions(fileName3, 317, 100),
                new CodebaseFileClocRevisions(fileName2, 132, 105),
                new CodebaseFileClocRevisions(fileName4, 44, 100),
                new CodebaseFileClocRevisions(fileName1, 30, 100),
                new CodebaseFileClocRevisions(fileName5, 22, 100)
        ), List.of(), List.of("java"));
        var clusteredClocRevisions = CodebaseClusteredClocRevisions.of(revisions);
        assertThat(clusteredClocRevisions).isEqualTo(expectedClusteredClocRevisions());
    }

    private CodebaseClusteredClocRevisions expectedClusteredClocRevisions() {
        List<List<CodebaseFileClocRevisions>> revisions = List.of(
                List.of(
                        new CodebaseFileClocRevisions(fileName3, 317, 100),
                        new CodebaseFileClocRevisions(fileName2, 132, 105),
                        new CodebaseFileClocRevisions(fileName4, 44, 100),
                        new CodebaseFileClocRevisions(fileName1, 30, 100),
                        new CodebaseFileClocRevisions(fileName5, 22, 100)
                ),
                List.of(
                        new CodebaseFileClocRevisions(fileName6, 17, 105),
                        new CodebaseFileClocRevisions(fileName0, 16, 100),
                        new CodebaseFileClocRevisions(fileName7, 8, 100)
                )
        );
        return new CodebaseClusteredClocRevisions(revisions, List.of(), List.of("java"));
    }
}