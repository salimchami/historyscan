package io.sch.historyscan

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.jupiter.api.Test

internal class ArchitectureTest {
    @Test
    fun `should domain contains only domains packages import`() {
        Konsist
            .scopeFromProject()
            .assertArchitecture {
                val infrastructure = Layer("Infrastructure", "io.sch.historyscan.infrastructure..")
                val domain = Layer("Domain", "io.sch.historyscan.domain..")
                infrastructure.dependsOn(domain)
                domain.dependsOnNothing()
            }
    }
}
