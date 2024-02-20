package io.sch.historyscan.domain.logging.spi

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI

@HexagonalArchitectureSPI
interface Logger {
    fun info(message: String)

    fun error(ex: Exception)

    fun error(message: String)

    fun debug(message: String)

    fun error(message: String, exception: Exception)
}
