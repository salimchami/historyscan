package io.sch.historyscan.infrastructure.features.analysis.exceptions;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class CodeBaseNotOpenedException extends HistoryScanTechnicalException {
    public CodeBaseNotOpenedException(String message, Throwable e) {
        super(message, e);
    }
}
