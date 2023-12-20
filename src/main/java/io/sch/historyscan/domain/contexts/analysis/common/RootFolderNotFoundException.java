package io.sch.historyscan.domain.contexts.analysis.common;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class RootFolderNotFoundException extends HistoryScanTechnicalException {
    public RootFolderNotFoundException(String message) {
        super(message);
    }
}
