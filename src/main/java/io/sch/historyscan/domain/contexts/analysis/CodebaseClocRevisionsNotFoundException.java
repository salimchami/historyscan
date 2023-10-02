package io.sch.historyscan.domain.contexts.analysis;

import io.sch.historyscan.domain.error.HistoryScanFunctionalException;

public class CodebaseClocRevisionsNotFoundException extends HistoryScanFunctionalException {
    public CodebaseClocRevisionsNotFoundException(String message) {
        super(message);
    }
}
