package io.sch.historyscan.domain.contexts.analysis;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

public class CodeBaseHistoryNotFoundException extends HistoryScanFunctionalException {
    public CodeBaseHistoryNotFoundException(String message) {
        super(message);
    }
}
