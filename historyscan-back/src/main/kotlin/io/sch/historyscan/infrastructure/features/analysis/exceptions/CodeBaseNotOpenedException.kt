package io.sch.historyscan.infrastructure.features.analysis.exceptions

import io.sch.historyscan.domain.error.HistoryScanTechnicalException

class CodeBaseNotOpenedException(message: String, e: Throwable) : HistoryScanTechnicalException(message, e)
