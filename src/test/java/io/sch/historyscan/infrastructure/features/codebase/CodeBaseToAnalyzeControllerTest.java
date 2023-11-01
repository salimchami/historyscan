package io.sch.historyscan.infrastructure.features.codebase;

import io.sch.historyscan.common.HistoryscanIntegrationTests;
import io.sch.historyscan.common.JsonReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static io.sch.historyscan.common.HistoryscanIntegrationTests.EndPoints.CODEBASES;
import static io.sch.historyscan.common.HistoryscanIntegrationTests.TestsFolders.CODEBASE_FOLDER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CodeBaseToAnalyzeControllerTest extends HistoryscanIntegrationTests {

    @Value("${io.sch.historyscan.codebases.folder}")
    private String codebasesFolder;

    public static Stream<Arguments> should_clone_the_codebase_params() {
        return Stream.of(
                Arguments.of("http-codebase", "http-added-codebase")
//                Arguments.of("git-codebase", "git-added-codebase")
        );
    }

    @ParameterizedTest(name = "{index} {1}")
    @MethodSource("should_clone_the_codebase_params")
    void should_clone_the_codebase(String codeBaseToAddJson, String expectedAddedCodeBase) throws Exception {
        final File codeBase = Paths.get(codebasesFolder, "public-articles").toFile();
        FileSystemUtils.deleteRecursively(codeBase);
        var expectedAddedCodebaseResponse = JsonReader.toExpectedJson(CODEBASE_FOLDER, expectedAddedCodeBase);
        var codebaseToAdd = JsonReader.toRequestedJson(CODEBASE_FOLDER, codeBaseToAddJson);
        endPointCaller.perform(post(CODEBASES).content(codebaseToAdd))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedAddedCodebaseResponse, true));
        codebaseExists(Paths.get(codebasesFolder, "public-articles").toString());
    }

    @Test
    void should_list_current_codebases() throws Exception {
        var expectedCodesBasesResponse = JsonReader.toExpectedJson(CODEBASE_FOLDER, "codebases-list");
        endPointCaller.perform(get(CODEBASES))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedCodesBasesResponse, true));
    }

    @Test
    void should_find_current_codebase() throws Exception {
        var expectedCodesBasesResponse = JsonReader.toExpectedJson(CODEBASE_FOLDER, "codebase-public-articles");
        endPointCaller.perform(get(CODEBASES + "/public-articles"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedCodesBasesResponse, true));
    }
}