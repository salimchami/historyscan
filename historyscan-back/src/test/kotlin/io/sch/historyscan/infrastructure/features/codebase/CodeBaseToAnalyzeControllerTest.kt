package io.sch.historyscan.infrastructure.features.codebase

import io.sch.historyscan.common.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.FileSystemUtils
import java.nio.file.Paths
import java.util.stream.Stream

@Disabled
internal class CodeBaseToAnalyzeControllerTest : HistoryscanIntegrationTests() {
    @Value("\${io.sch.historyscan.codebases.folder}")
    lateinit var codebasesFolder: String

    @ParameterizedTest(name = "{index} {1}")
    @MethodSource("should_clone_the_codebase_params")
    @Throws(
        Exception::class
    )
    fun should_clone_the_codebase(codeBaseToAddJson: String, expectedAddedCodeBase: String) {
        val codeBase = Paths.get(codebasesFolder, "public-articles").toFile()
        FileSystemUtils.deleteRecursively(codeBase)
        val expectedAddedCodebaseResponse = JsonReader.toExpectedJson(TestsFolders.ANALYSIS, expectedAddedCodeBase)
        val codebaseToAdd = JsonReader.toRequestedJson(TestsFolders.ANALYSIS, codeBaseToAddJson)
        endPointCaller.perform(MockMvcRequestBuilders.post(EndPoints.CODEBASES).content(codebaseToAdd))
            ?.andExpect(MockMvcResultMatchers.status().isCreated())
            ?.andExpect(MockMvcResultMatchers.content().json(expectedAddedCodebaseResponse, true))
        codebaseExists(Paths.get(codebasesFolder, "public-articles").toString())
    }

    @Test
    @Throws(Exception::class)
    fun should_list_current_codebases() {
        val expectedCodesBasesResponse = JsonReader.toExpectedJson(TestsFolders.ANALYSIS, "codebases-list")
        endPointCaller.perform(MockMvcRequestBuilders.get(EndPoints.CODEBASES))
            ?.andExpect(MockMvcResultMatchers.status().isOk())
            ?.andExpect(MockMvcResultMatchers.content().json(expectedCodesBasesResponse, true))
    }

    @Test
    @Throws(Exception::class)
    fun should_find_current_codebase() {
        val expectedCodesBasesResponse = JsonReader.toExpectedJson(TestsFolders.ANALYSIS, "codebase-public-articles")
        endPointCaller.perform(MockMvcRequestBuilders.get(EndPoints.CODEBASES + "/public-articles"))
            ?.andExpect(MockMvcResultMatchers.status().isOk())
            ?.andExpect(MockMvcResultMatchers.content().json(expectedCodesBasesResponse, true))
    }

    companion object {
        @JvmStatic
        fun should_clone_the_codebase_params(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of("http-codebase", "http-added-codebase"),
                Arguments.of("git-codebase", "git-added-codebase")
            )
        }
    }
}