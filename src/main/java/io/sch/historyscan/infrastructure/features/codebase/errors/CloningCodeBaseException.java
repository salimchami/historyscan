package io.sch.historyscan.infrastructure.features.codebase.errors;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class CloningCodeBaseException extends HistoryScanTechnicalException {
    public CloningCodeBaseException(String message, Throwable e) {
        super(message, e);
    }
}
