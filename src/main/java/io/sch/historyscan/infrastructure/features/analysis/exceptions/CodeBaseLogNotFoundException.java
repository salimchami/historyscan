package io.sch.historyscan.infrastructure.features.analysis.exceptions;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class CodeBaseLogNotFoundException extends HistoryScanTechnicalException {
    public CodeBaseLogNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
