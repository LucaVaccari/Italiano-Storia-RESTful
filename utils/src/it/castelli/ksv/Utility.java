package it.castelli.ksv;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Utility {
	/**
	 * Returns a LocalDate from the specified milliseconds
	 *
	 * @param millis The milliseconds passed from 01-01-1970
	 * @return The LocalDate object
	 */
	public static LocalDate dateFromMillis(long millis) {
		return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
