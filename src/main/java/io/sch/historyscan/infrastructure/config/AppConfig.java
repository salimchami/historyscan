package io.sch.historyscan.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.time.Clock;

@Configuration
public class AppConfig implements AsyncConfigurer {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
