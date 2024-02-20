package io.sch.historyscan.infrastructure.config

import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import java.time.Clock

@Configuration
class AppConfig : AsyncConfigurer {
    @Bean
    fun clock(): Clock {
        return Clock.systemDefaultZone()
    }

    @Bean
    fun cacheManager(): CacheManager {
        return ConcurrentMapCacheManager()
    }
}
