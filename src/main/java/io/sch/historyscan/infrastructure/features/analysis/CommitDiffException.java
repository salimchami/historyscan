package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class CommitDiffException extends HistoryScanTechnicalException {
    public CommitDiffException(String message, Exception e) {
        super(message, e);
    }

    public CommitDiffException(String message) {
        super(message);
    }
}
