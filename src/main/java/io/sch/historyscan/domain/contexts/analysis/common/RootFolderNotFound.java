package io.sch.historyscan.domain.contexts.analysis.common;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class RootFolderNotFound extends HistoryScanTechnicalException {
    public RootFolderNotFound(String message) {
        super(message);
    }
}
