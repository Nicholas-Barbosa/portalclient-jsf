package com.portal.jse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class LocalDateTest {

	@Test
	void test() {
		String dateToParse = "2020-07-27T14:48:34";
		int lasIndexOf = dateToParse.lastIndexOf("-");

		System.out.println(LocalDateTime.now());
		System.out.println(LocalDateTime.parse(dateToParse, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
	}

}
