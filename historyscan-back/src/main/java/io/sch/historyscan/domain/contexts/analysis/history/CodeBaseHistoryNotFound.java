package io.sch.historyscan.domain.contexts.analysis.history;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

public class CodeBaseHistoryNotFound extends HistoryScanFunctionalException {
    public CodeBaseHistoryNotFound(String message) {
        super(message);
    }
}
