package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

import io.sch.historyscan.domain.error.HistoryScanTechnicalException
import java.io.IOException

class CodebasePathCanNotBeRead(message: String, e: IOException) : HistoryScanTechnicalException(message, e)
