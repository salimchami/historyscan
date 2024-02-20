package io.sch.historyscan.common

import io.sch.historyscan.HistoryscanApplication
import io.sch.historyscan.domain.contexts.analysis.history.HistoryAnalyzer
import io.sch.historyscan.fake.CodeBaseCommitFake
import io.sch.historyscan.infrastructure.common.filesystem.FileSystemManager
import io.sch.historyscan.infrastructure.config.AppConfig
import io.sch.historyscan.infrastructure.config.HateoasConfig
import io.sch.historyscan.infrastructure.features.filesystem.FileSystemReader
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.fail
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.test.web.servlet.MockMvc
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@SpringBootTest(classes = [HistoryscanApplication::class])
@ContextConfiguration(
    classes = [AppConfig::class, HateoasConfig::class, UTF8Config::class
    ]
)
@AutoConfigureMockMvc
abstract class HistoryscanIntegrationTests : InitializingBean {
    @MockBean
    lateinit var codeBaseHistoryAnalyzer: HistoryAnalyzer

    @MockBean
    lateinit var fileSystemManager: FileSystemManager

    @Autowired
    lateinit var fileSystemReader: FileSystemReader

    @BeforeEach
    @Throws(IOException::class)
    fun setUp() {
        val codebasesResource = ClassPathResource("codebases")
        ReflectionTestUtils.setField(fileSystemReader, "codebasesFolder", codebasesResource.file.path)
        Mockito.`when`(codeBaseHistoryAnalyzer.of("theglobalproject")).thenReturn(CodeBaseCommitFake.defaultHistory())
        val codebaseResource = ClassPathResource("codebases/theglobalproject")
        Mockito.`when`(
            fileSystemManager.findFolder(
                anyString(),
                eq("theglobalproject")
            )
        )
            .thenReturn(codebaseResource.file)
    }

    lateinit var endPointCaller: EndPointCaller

    @Autowired
    lateinit var mockMvc: MockMvc

    override fun afterPropertiesSet() {
        endPointCaller = EndPointCaller(mockMvc)
    }

    protected fun codebaseExists(folderName: String) {
        if (!Files.exists(Paths.get(folderName))) {
            fail("The codebase folder $folderName does not exist")
        }
    }

    object EndPoints {
        const val BASE_URL: String = "/api/v1"
        const val CODEBASES: String = "$BASE_URL/codebases"
    }

    object TestsFolders {
        const val APP_STARTUP_FOLDER: String = "app-startup"
        const val ANALYSIS: String = "analysis"
    }
}
