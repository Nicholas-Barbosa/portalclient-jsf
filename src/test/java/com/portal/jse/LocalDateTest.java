package com.portal.jse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class LocalDateTest {

	@Test
	void test() {
		String dateToParse = "2021-06-04T19:29:21-03:00";
		int lasIndexOf = dateToParse.lastIndexOf("-");
		
		System.out.println(LocalDateTime.now());
		System.out.println(DateTimeFormatter.ofPattern("yyyy-dd-MM'T'HH:mm:ss"). parse(dateToParse.substring(0,lasIndexOf)));
	}

}
