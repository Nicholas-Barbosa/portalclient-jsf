package com.portal.client.dto.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateParser {

	public static LocalDate convert(String date) {
		return LocalDate.parse(date.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}
