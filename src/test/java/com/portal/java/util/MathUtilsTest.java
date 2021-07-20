package com.portal.java.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.portal.client.util.MathUtils;

class MathUtilsTest {

	@Test
	void testPercentage() {
		BigDecimal value1 = new BigDecimal(2500);
		BigDecimal value2 = new BigDecimal(1250);
		assertEquals(50, MathUtils.findHwManyPercentsYCorrespondsOverX(value2, value1).doubleValue());
	}

	@Test
	void testPercentage2() {
		BigDecimal value1 = new BigDecimal(2500);
		BigDecimal value2 = new BigDecimal(12);
		assertEquals(300, MathUtils.findHwMuchXPercentCorrespondsOverWholeValue(value2, value1).doubleValue());
	}
}
