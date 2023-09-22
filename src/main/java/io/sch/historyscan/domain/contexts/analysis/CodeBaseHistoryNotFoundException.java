package io.sch.historyscan.domain.contexts.analysis;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class CodeBaseHistoryNotFoundException extends HistoryScanTechnicalException {
    public CodeBaseHistoryNotFoundException(String message) {
        super(message);
    }
}
