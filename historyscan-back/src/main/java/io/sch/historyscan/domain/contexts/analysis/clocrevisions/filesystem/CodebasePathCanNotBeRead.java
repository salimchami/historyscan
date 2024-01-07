package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

import java.io.IOException;

public class CodebasePathCanNotBeRead extends HistoryScanTechnicalException {
    public CodebasePathCanNotBeRead(String message, IOException e) {
        super(message, e);

    }
}
