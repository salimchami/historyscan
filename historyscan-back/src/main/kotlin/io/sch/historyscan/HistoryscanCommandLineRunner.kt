package io.sch.historyscan

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("cmd-line")
class HistoryscanCommandLineRunner : CommandLineRunner {
    override fun run(vararg args: String) {
        throw UnsupportedOperationException("Not implemented yet")
    }
}
