package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

import java.io.IOException;

public class CodebasePathCanNotBeReadException extends HistoryScanTechnicalException {
    public CodebasePathCanNotBeReadException(String message, IOException e) {
        super(message, e);

    }
}
