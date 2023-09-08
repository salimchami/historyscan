package io.sch.historyscan.infrastructure.features.codebase;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class CreatingCodeBaseFolderException extends HistoryScanTechnicalException {

    public CreatingCodeBaseFolderException(String message, Throwable e) {
        super(message, e);
    }
}
