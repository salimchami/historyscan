package io.sch.historyscan.domain.contexts.codebase.clone

import io.sch.historyscan.domain.error.HistoryScanFunctionalException

class CodeBaseAlreadyExistsException(message: String) : HistoryScanFunctionalException(message)
