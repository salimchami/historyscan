package io.sch.historyscan.domain.contexts.analysis.common;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class ScanTypeNotFound extends HistoryScanTechnicalException {
    public ScanTypeNotFound(String message) {
        super(message);
    }
}
