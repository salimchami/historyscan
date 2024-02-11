package io.sch.historyscan.infrastructure.features.analysis.exceptions

import io.sch.historyscan.domain.error.HistoryScanTechnicalException

class CodeBaseLogNotFoundException(message: String, e: Throwable) : HistoryScanTechnicalException(message, e)
