package io.sch.historyscan.infrastructure.features.analysis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class FileLinesCountTest {

    private byte[] fileContent;

    @BeforeEach
    void setUp() throws IOException {
         var file =  new ClassPathResource("codebases/theglobalproject/src/main/java/io/mycompany/" +
                                                   "theproject/domain/boundedcontexts/featureA/Loading.java").getFile();
        fileContent = Files.readAllBytes(file.toPath());
    }

    @Test
    void should_count_added_modified_deleted_and_actual_lines() {
          var fileLinesCount = FileLinesCount.from(
                  new String[]{"+line1", "-line2", "+++line3", "---line4"},
                  fileContent, "Loading.java");
          assertEquals(32, fileLinesCount.nbLines());
          assertEquals(1, fileLinesCount.addedLines());
          assertEquals(1, fileLinesCount.deletedLines());
          assertEquals(2, fileLinesCount.modifiedLines());
    }
}