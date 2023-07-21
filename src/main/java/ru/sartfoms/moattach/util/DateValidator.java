package ru.sartfoms.moattach.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateValidator {
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd");
	public static boolean isValid(String dateStr) {
		try {
			DATE_TIME_FORMATTER.withResolverStyle(ResolverStyle.STRICT).parse(dateStr);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}
}