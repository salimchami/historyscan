package io.sch.historyscan.infrastructure.logging

import io.sch.historyscan.domain.logging.spi.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AppLogger : Logger {
    override fun info(message: String) {
        logger.info(message)
    }

    override fun error(ex: Exception) {
        logger.error(ex.message, ex)
    }

    override fun error(message: String) {
        logger.error(message)
    }

    override fun debug(message: String) {
        logger.debug(message)
    }

    override fun error(message: String, exception: Exception) {
        logger.error(message, exception)
    }

    companion object {
        private val logger: org.slf4j.Logger = LoggerFactory.getLogger(AppLogger::class.java)
    }
}
