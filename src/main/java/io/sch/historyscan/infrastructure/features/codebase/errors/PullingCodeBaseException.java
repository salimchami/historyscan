package io.sch.historyscan.infrastructure.features.codebase.errors;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class PullingCodeBaseException extends HistoryScanTechnicalException {

    public PullingCodeBaseException(String message, Throwable e) {
        super(message, e);
    }
}
