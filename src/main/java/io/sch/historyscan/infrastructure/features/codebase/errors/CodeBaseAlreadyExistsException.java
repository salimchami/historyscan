package io.sch.historyscan.infrastructure.features.codebase.errors;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

public class CodeBaseAlreadyExistsException extends HistoryScanFunctionalException {
    public CodeBaseAlreadyExistsException(String message) {
        super(message);
    }
}
