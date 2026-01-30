package bookstore.utils;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    public static OffsetDateTime truncateToMicros(OffsetDateTime value) {
        if (value == null) return null;
        return value.truncatedTo(ChronoUnit.MICROS);
    }
}
