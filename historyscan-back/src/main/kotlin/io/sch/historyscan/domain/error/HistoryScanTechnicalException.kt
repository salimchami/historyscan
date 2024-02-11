package io.sch.historyscan.domain.error

open class HistoryScanTechnicalException : RuntimeException {
    constructor(message: String, e: Throwable) : super(message, e)
    constructor(message: String) : super(message)
}
