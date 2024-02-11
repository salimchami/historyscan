package io.sch.historyscan.infrastructure.features.codebase.info

import io.sch.historyscan.domain.error.HistoryScanTechnicalException

class CodeBaseGitFolderNotFoundException(message: String?) : HistoryScanTechnicalException(message)
