package io.sch.historyscan.infrastructure.common.filesystem;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class FolderNotReadableException extends HistoryScanTechnicalException {
    public FolderNotReadableException(String message, Throwable e) {
        super(message, e);
    }
}
