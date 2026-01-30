package bookstore.utils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DateUtils {

    public static OffsetDateTime nowUtcMillis(){
        return truncateToMillis(OffsetDateTime.now(ZoneOffset.UTC));
    }

    public static OffsetDateTime truncateToMillis(OffsetDateTime value) {
        if (value == null) return null;
        return value.withNano((value.getNano() / 1_000_000) * 1_000_000);
    }
}
