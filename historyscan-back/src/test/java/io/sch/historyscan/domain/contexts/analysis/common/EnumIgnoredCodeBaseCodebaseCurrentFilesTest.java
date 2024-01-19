package io.sch.historyscan.domain.contexts.analysis.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.sch.historyscan.domain.contexts.analysis.common.EnumIgnoredCodeBaseFiles.isIgnored;
import static org.assertj.core.api.Assertions.assertThat;

class EnumIgnoredCodeBaseFilesTest {

    @Test
    @DisplayName("path is part of codebases folder (especially in tests)")
    void should_not_ignore_where_path_is_part_of_codebases_folder() {
        String path = "history-scan/historyscan-back/build/resources/test/codebases/theglobalproject";
        var ignoredFile = isIgnored("C:/Users/slim/IdeaProjects/edukativ/history-scan/" +
                                    "historyscan-back/build/resources/test/codebases/theglobalproject",
                path, false);
        assertThat(ignoredFile).isFalse();
    }

    @Test
    void should_ignore_html_report_folder() {
        String path = "root/build/path/to/htmlReport/file.html";
        var ignoredFile = isIgnored("root/build/path", path, true);
        assertThat(ignoredFile).isTrue();
    }

    @Test
    void should_not_ignore_html_folder() {
        String path = "root/build/path/templates/file.html";
        var ignoredFile = isIgnored("root/build/path", path, true);
        assertThat(ignoredFile).isFalse();
    }

    @Test
    void should_ignore_lib_folder() {
        String path = "root/build/path/project/lib";
        var ignoredFile = isIgnored("root/build/path", path, false);
        assertThat(ignoredFile).isTrue();
    }

    @Test
    void should_not_ignore_contexts_folder() {
        String path = "root/build/path/project/contexts";
        var ignoredFile = isIgnored("root/build/path", path, false);
        assertThat(ignoredFile).isFalse();
    }
}