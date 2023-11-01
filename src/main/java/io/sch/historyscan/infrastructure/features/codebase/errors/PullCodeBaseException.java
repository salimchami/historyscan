package io.sch.historyscan.infrastructure.features.codebase.errors;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class PullCodeBaseException extends HistoryScanTechnicalException {

    public PullCodeBaseException(String message, Throwable e) {
        super(message, e);
    }
}
