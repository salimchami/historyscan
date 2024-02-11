package io.sch.historyscan.infrastructure.features.analysis.exceptions

import io.sch.historyscan.domain.error.HistoryScanTechnicalException

class CommitDiffException(message: String, e: Exception) : HistoryScanTechnicalException(message, e)
