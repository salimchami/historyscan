package io.sch.historyscan.infrastructure.features.llm.client;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class UnableToSerializeCodebaseNode extends HistoryScanTechnicalException {
    public UnableToSerializeCodebaseNode(String message, Exception e) {
        super(message, e);
    }
}
