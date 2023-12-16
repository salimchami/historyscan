package io.sch.historyscan.common;

import io.sch.historyscan.HistoryscanApplication;
import io.sch.historyscan.infrastructure.config.AppConfig;
import io.sch.historyscan.infrastructure.config.HateoasConfig;
import io.sch.historyscan.infrastructure.features.analysis.CodeBaseHistoryAnalyzer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static io.sch.historyscan.fake.CodeBaseCommitFake.defaultHistory;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = HistoryscanApplication.class)
@ContextConfiguration(classes = {
        AppConfig.class,
        HateoasConfig.class,
        UTF8Config.class
})
@AutoConfigureMockMvc
public abstract class HistoryscanIntegrationTests implements InitializingBean {

    @MockBean
    private CodeBaseHistoryAnalyzer codeBaseHistoryAnalyzer;

    @BeforeEach
    void setUp() {
        var history = Optional.of(defaultHistory());
        when(codeBaseHistoryAnalyzer.of(anyString())).thenReturn(history);
    }

    protected EndPointCaller endPointCaller;
    @Autowired
    private MockMvc mockMvc;

    @Override
    public void afterPropertiesSet() {
        endPointCaller = new EndPointCaller(mockMvc);
    }

    protected void codebaseExists(String folderName) {
        if (!Files.exists(Paths.get(folderName))) {
            fail("The codebase folder " + folderName + " does not exist");
        }
    }

    public static class EndPoints {
        public static final String BASE_URL = "/api/v1";
        public static final String CODEBASES = BASE_URL + "/codebases";
    }

    public static class TestsFolders {
        public static final String APP_STARTUP_FOLDER = "app-startup";
        public static final String CODEBASE_FOLDER = "codebases";
    }
}
