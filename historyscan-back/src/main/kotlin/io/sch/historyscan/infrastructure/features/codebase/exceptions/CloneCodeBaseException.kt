package io.sch.historyscan.infrastructure.features.codebase.exceptions

import io.sch.historyscan.domain.error.HistoryScanTechnicalException

class CloneCodeBaseException : HistoryScanTechnicalException {
    constructor(message: String, e: Throwable) : super(message, e)

    constructor(message: String) : super(message)
}
