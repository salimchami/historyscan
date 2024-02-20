package io.sch.historyscan.fake

import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneId

object LocalDateTimeFake {
    fun now(): LocalDateTime? {
        val zone = ZoneId.systemDefault()
        val defaultDateTime = LocalDateTime.of(2022, 2, 22, 22, 22, 22)
        val defaultFixedClock = Clock.fixed(defaultDateTime!!.atZone(zone).toInstant(), zone)
        return LocalDateTime.now(defaultFixedClock)
    }
}
