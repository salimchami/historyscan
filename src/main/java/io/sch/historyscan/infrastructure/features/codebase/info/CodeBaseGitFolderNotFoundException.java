package io.sch.historyscan.infrastructure.features.codebase.info;

import io.sch.historyscan.domain.error.HistoryScanTechnicalException;

public class CodeBaseGitFolderNotFoundException extends HistoryScanTechnicalException {
    public CodeBaseGitFolderNotFoundException(String message) {
        super(message);
    }
}
