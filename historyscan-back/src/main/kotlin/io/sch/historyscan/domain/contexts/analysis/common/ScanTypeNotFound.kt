package io.sch.historyscan.domain.contexts.analysis.common

import io.sch.historyscan.domain.error.HistoryScanTechnicalException

class ScanTypeNotFound(message: String) : HistoryScanTechnicalException(message)
