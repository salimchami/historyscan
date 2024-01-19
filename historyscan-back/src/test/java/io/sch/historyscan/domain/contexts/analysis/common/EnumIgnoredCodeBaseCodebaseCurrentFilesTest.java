package io.sch.historyscan.domain.contexts.analysis.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnumIgnoredCodeBaseFilesTest {

    @Test
    void should_not_ignore_build_in_root_folder_html_report_folder() {
        String path = "root/path/build/htmlReport/file.html";
        var ignoredFile = EnumIgnoredCodeBaseFiles.isIgnored("path/build", path, true);
        assertThat(ignoredFile).isFalse();
    }

    @Test
    void should_ignore_html_report_folder() {
        String path = "root/path/to/htmlReport/file.html";
        var ignoredFile = EnumIgnoredCodeBaseFiles.isIgnored("root", path, true);
        assertThat(ignoredFile).isTrue();
    }

    @Test
    void should_not_ignore_html_folder() {
        String path = "root/templates/file.html";
        var ignoredFile = EnumIgnoredCodeBaseFiles.isIgnored("root", path, true);
        assertThat(ignoredFile).isFalse();
    }

    @Test
    void should_ignore_lib_folder() {
        String path = "root/lib";
        var ignoredFile = EnumIgnoredCodeBaseFiles.isIgnored("root", path, false);
        assertThat(ignoredFile).isTrue();
    }
    @Test
    void should_not_ignore_contexts_folder() {
        String path = "root/contexts";
        var ignoredFile = EnumIgnoredCodeBaseFiles.isIgnored("root", path, false);
        assertThat(ignoredFile).isFalse();
    }
}