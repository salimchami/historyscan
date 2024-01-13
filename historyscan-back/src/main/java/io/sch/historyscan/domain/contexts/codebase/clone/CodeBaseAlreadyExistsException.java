package io.sch.historyscan.domain.contexts.codebase.clone;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

public class CodeBaseAlreadyExistsException extends HistoryScanFunctionalException {
    public CodeBaseAlreadyExistsException(String message) {
        super(message);
    }
}
