package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class CodeBaseHeadNotFoundException extends HistoryScanTechnicalException {
    public CodeBaseHeadNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
