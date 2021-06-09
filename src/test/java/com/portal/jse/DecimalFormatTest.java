package com.portal.jse;

import java.text.NumberFormat;

import org.junit.jupiter.api.Test;

class DecimalFormatTest {

	@Test
	void test() {
		NumberFormat nFormat = NumberFormat.getPercentInstance();

		System.out.println(nFormat.format(0.0025));

	}

}
