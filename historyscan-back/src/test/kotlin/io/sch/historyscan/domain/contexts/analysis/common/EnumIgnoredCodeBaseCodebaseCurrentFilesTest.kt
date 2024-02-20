package io.sch.historyscan.domain.contexts.analysis.common

import io.sch.historyscan.domain.contexts.analysis.common.EnumIgnoredCodeBaseFiles.Companion.isIgnored
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class EnumIgnoredCodeBaseFilesTest {
    @Test
    @DisplayName("path is part of codebases folder (especially in tests)")
    fun should_not_ignore_where_path_is_part_of_codebases_folder() {
        val path = "history-scan/historyscan-back/build/resources/test/codebases/theglobalproject"
        val ignoredFile = isIgnored(
            "C:/Users/slim/IdeaProjects/edukativ/history-scan/" +
                    "historyscan-back/build/resources/test/codebases/theglobalproject",
            path, false
        )
        Assertions.assertThat(ignoredFile).isFalse()
    }

    @Test
    fun should_ignore_html_report_folder() {
        val path = "root/build/path/to/htmlReport/file.html"
        val ignoredFile = isIgnored("root/build/path", path, true)
        Assertions.assertThat(ignoredFile).isTrue()
    }

    @Test
    fun should_not_ignore_html_folder() {
        val path = "root/build/path/templates/file.html"
        val ignoredFile = isIgnored("root/build/path", path, true)
        Assertions.assertThat(ignoredFile).isFalse()
    }

    @Test
    fun should_ignore_lib_folder() {
        val path = "root/build/path/project/lib"
        val ignoredFile = isIgnored("root/build/path", path, false)
        Assertions.assertThat(ignoredFile).isTrue()
    }

    @Test
    fun should_not_ignore_contexts_folder() {
        val path = "root/build/path/project/contexts"
        val ignoredFile = isIgnored("root/build/path", path, false)
        Assertions.assertThat(ignoredFile).isFalse()
    }
}