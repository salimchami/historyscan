package io.sch.historyscan.domain.contexts.analysis.common;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class ScanTypeNotFoundException extends HistoryScanTechnicalException {
    public ScanTypeNotFoundException(String message) {
        super(message);
    }
}
