package io.sch.historyscan.fake;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeFake {

    public static LocalDateTime now() {
        var zone = ZoneId.systemDefault();
        var defaultDateTime = LocalDateTime.of(2022, 2, 22, 22, 22, 22);
        var defaultFixedClock = Clock.fixed(defaultDateTime.atZone(zone).toInstant(), zone);
        return LocalDateTime.now(defaultFixedClock);
    }
}
