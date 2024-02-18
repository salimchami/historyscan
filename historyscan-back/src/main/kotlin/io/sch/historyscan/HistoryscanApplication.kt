package io.sch.historyscan

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class HistoryscanApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<HistoryscanApplication>(*args)
        }
    }
}
