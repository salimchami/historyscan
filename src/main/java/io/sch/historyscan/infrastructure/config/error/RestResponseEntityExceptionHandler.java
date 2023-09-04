package io.sch.historyscan.infrastructure.config.error;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;
import io.sch.historyscan.domain.error.HistoryScanTechnicalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {HistoryScanTechnicalException.class})
    public ResponseEntity<Object> handleHistoryScanTechnicalException(Exception ex, WebRequest request) {
        return handleGeneralException(ex, request);
    }

    @ExceptionHandler(value = {HistoryScanFunctionalException.class})
    public ResponseEntity<Object> handleHistoryScanFunctionalException(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(ex.getMessage(), request.getContextPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(ex.getMessage(), request.getContextPath());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
