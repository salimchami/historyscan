package io.sch.historyscan.infrastructure.features.codebase.exceptions;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class CloneCodeBaseException extends HistoryScanTechnicalException {
    public CloneCodeBaseException(String message, Throwable e) {
        super(message, e);
    }

    public CloneCodeBaseException(String message) {
        super(message);
    }
}
