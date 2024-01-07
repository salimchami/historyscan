package io.sch.historyscan.domain.error;

public class HistoryScanTechnicalException extends RuntimeException {
    public HistoryScanTechnicalException(String message, Throwable e) {
        super(message, e);
    }

    public HistoryScanTechnicalException(String message) {
        super(message);
    }
}
