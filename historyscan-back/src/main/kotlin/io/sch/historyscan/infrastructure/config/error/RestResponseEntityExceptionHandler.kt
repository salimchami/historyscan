package io.sch.historyscan.infrastructure.config.error

import io.sch.historyscan.domain.error.HistoryScanFunctionalException
import io.sch.historyscan.domain.error.HistoryScanTechnicalException
import io.sch.historyscan.domain.logging.spi.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler(private val appLogger: Logger) : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [HistoryScanTechnicalException::class])
    fun handleHistoryScanTechnicalException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        return handleGeneralException(ex, request)
    }

    @ExceptionHandler(value = [HistoryScanFunctionalException::class])
    fun handleHistoryScanFunctionalException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(ex.message, request.contextPath)
        appLogger.error(ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleGeneralException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val apiError = ApiError(ex.message, request.contextPath)
        appLogger.error(ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError)
    }
}
