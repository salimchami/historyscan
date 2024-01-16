package io.sch.historyscan.domain.contexts.analysis.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnumIgnoredCodeBaseCodebaseCurrentFilesTest {

    @Test
    void should_ignore_html_report_folder() {
        String path = "/root/path/to/htmlReport/file.html";
        var ignoredFile = EnumIgnoredCodeBaseFiles.isIgnored(path, false);
        assertThat(ignoredFile).isTrue();
    }
}