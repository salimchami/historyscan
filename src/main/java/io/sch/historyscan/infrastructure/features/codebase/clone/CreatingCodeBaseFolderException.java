package io.sch.historyscan.infrastructure.features.codebase.clone;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class CreatingCodeBaseFolderException extends HistoryScanTechnicalException {

    public CreatingCodeBaseFolderException(String message, Throwable e) {
        super(message, e);
    }
}