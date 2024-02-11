package io.sch.historyscan.infrastructure.features.codebase.exceptions

import io.sch.historyscan.domain.error.HistoryScanTechnicalException

class PullCodeBaseException(message: String?, e: Throwable?) : HistoryScanTechnicalException(message, e)
