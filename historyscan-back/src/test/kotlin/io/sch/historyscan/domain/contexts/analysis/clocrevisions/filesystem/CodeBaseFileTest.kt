package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.RootFolder.Companion.of
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.core.io.ClassPathResource
import java.io.File
import java.io.IOException
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CodeBaseFileTest {
    private var codebaseFile: File? = null
    private var codebasesFile: File? = null

    @BeforeEach
    @Throws(IOException::class)
    fun setUp() {
        val codebaseResource = ClassPathResource("codebases/theglobalproject")
        val codebasesResource = ClassPathResource("codebases")
        codebaseFile = codebaseResource.file
        codebasesFile = codebasesResource.file
    }

    @ParameterizedTest
    @MethodSource("should_find_children_from_root_folder_and_without_ignored_files_params")
    fun should_find_children_from_root_folder_and_without_ignored_files(
        rootFolderValue: String?,
        expectedFiles: MutableList<String?>?
    ) {
        val codeBaseName = "theglobalproject"
        val rootFolder = of(rootFolderValue!!, codeBaseName)
        val sut = CodeBaseFile(codebaseFile!!, rootFolder, codebasesFile!!.path)
        val children = sut.filteredChildren()
        Assertions.assertThat(children)
            .extracting<String?, RuntimeException?>(FileInfo::name)
            .containsExactlyInAnyOrderElementsOf(expectedFiles)
    }

    companion object {
        @JvmStatic
        fun should_find_children_from_root_folder_and_without_ignored_files_params(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(
                    "/", listOf<String?>(
                        "src",
                        "main",
                        "java",
                        "io",
                        "mycompany",
                        "theproject",
                        "domain",
                        "boundedcontexts",
                        "featureA",
                        "refactored",
                        "Extensions.java",
                        "Loading.java",
                        "Extensions.java",
                        "Loading.java",
                        "featureB",
                        "FinallyLoading.java",
                        "infrastructure",
                        "boundedcontexts",
                        "featureA",
                        "ExtensionsAdapter.java",
                        "build.gradle",
                        "thelastfeature",
                        "HexagonalFeature.java"
                    )
                ),
                Arguments.of(
                    "domain", listOf<String?>(
                        "domain",
                        "boundedcontexts",
                        "featureA",
                        "refactored",
                        "Extensions.java",
                        "Loading.java",
                        "Extensions.java",
                        "Loading.java",
                        "featureB",
                        "FinallyLoading.java",
                        "thelastfeature",
                        "HexagonalFeature.java"
                    )
                )
            )
        }
    }
}