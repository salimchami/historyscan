package io.sch.historyscan.infrastructure.features.analysis

import io.sch.historyscan.infrastructure.features.analysis.FileLinesCount.Companion.from
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource
import java.nio.file.Files

internal class FileLinesCountTest {

    @Test
    fun should_count_added_modified_deleted_and_actual_lines() {
        val file = ClassPathResource(
            "codebases/theglobalproject/src/main/java/io/mycompany/" +
                    "theproject/domain/boundedcontexts/featureA/Loading.java"
        ).file
        val fileContent = Files.readAllBytes(file.toPath())
        val fileLinesCount = from(
            arrayOf("+line1", "-line2", "+++line3", "---line4"),
            fileContent, "Loading.java"
        )
        Assertions.assertEquals(32, fileLinesCount.nbLines)
        Assertions.assertEquals(1, fileLinesCount.addedLines)
        Assertions.assertEquals(1, fileLinesCount.deletedLines)
        Assertions.assertEquals(2, fileLinesCount.modifiedLines)
    }
}