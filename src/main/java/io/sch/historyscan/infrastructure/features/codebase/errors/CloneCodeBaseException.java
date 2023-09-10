package io.sch.historyscan.infrastructure.features.codebase.errors;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class CloneCodeBaseException extends HistoryScanTechnicalException {
    public CloneCodeBaseException(String message, Throwable e) {
        super(message, e);
    }
}
