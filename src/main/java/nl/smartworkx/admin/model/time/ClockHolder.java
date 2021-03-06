package nl.smartworkx.admin.model.time;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * @author Joris Wijlens
 * @version 1.0
 * @since 1.0
 */
public final class ClockHolder {

    private static final ZoneId DEFAULT_TIME_ZONE = ZoneId.of("CET");

    private static Clock CLOCK = Clock.systemDefaultZone();

    private static void setClock(Clock clock) {

        CLOCK = clock;
    }

    public static Clock getClock() {

        return CLOCK;
    }

    /**
     * For test purposes only
     *
     * @param dateAsString
     */
    public static void setClock(String dateAsString) {

        setClock(Clock.fixed(DateUtils.dateToDateTime(dateAsString).toInstant(ZoneOffset.MIN), DEFAULT_TIME_ZONE));
    }

    public static void resetClock() {
        Clock.systemDefaultZone();
    }
}
