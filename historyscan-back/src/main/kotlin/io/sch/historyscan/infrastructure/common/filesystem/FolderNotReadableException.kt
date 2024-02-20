package io.sch.historyscan.infrastructure.common.filesystem

import io.sch.historyscan.domain.error.HistoryScanTechnicalException

class FolderNotReadableException(message: String, e: Throwable) : HistoryScanTechnicalException(message, e)
