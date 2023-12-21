package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClocRevisionsFileTest {

    public static Stream<Arguments> should_create_cloc_revisions_file_params() {
        return Stream.of(
                Arguments.of("src", "src"),
                Arguments.of("main", "src/main"),
                Arguments.of("java", "src/main/java"),
                Arguments.of("io.mystartup.project", "src/main/java/io.mystartup.project"),
                Arguments.of("TheApplication.java", "src/main/java/io.mystartup.project/TheApplication.java")
        );
    }

    @ParameterizedTest
    @MethodSource("should_create_cloc_revisions_file_params")
    void should_create_path_from_part(String part, String expectedPath) {
        var sut = new ClocRevisionsFile(new FileInfo("TheApplication.java", "src/main/java/io.mystartup.project/TheApplication.java"), new RevisionScore(1));
        var path = sut.pathFrom(part);
        assertEquals(expectedPath, path);
    }
}