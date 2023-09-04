package io.sch.historyscan.common;

import io.sch.historyscan.HistoryscanApplication;
import io.sch.historyscan.infrastructure.config.AppConfig;
import io.sch.historyscan.infrastructure.config.HateoasConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
    protected Clock clock;

    protected EndPointCaller endPointCaller;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        when(clock.instant()).thenReturn(Dates.defaultFixedClock.instant());
        when(clock.getZone()).thenReturn(Dates.zone);
    }

    @Override
    public void afterPropertiesSet() {
        endPointCaller = new EndPointCaller(mockMvc);
    }

    protected static class Dates {
        private static final ZoneId zone = ZoneId.systemDefault();
        private static final LocalDateTime defaultDateTime = LocalDateTime.of(2022, 2, 22, 22, 22, 22);
        private static final Clock defaultFixedClock = Clock.fixed(defaultDateTime.atZone(zone).toInstant(), zone);
    }

    public static class EndPoints {
        public static final String BASE_URL = "/";
    }

    public static class TestsFolders {
        public static final String APP_STARTUP_FOLDER = "app-startup";
    }
}
