package it.castelli.ksv;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Utility {
    public static LocalDate dateFromMillis(long millis) {
        return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
