package com.portal;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class BigDecimalTests {

	

	@Test
	void test() {
		BigDecimal v1 = new BigDecimal(8.48);
		BigDecimal v2 = new BigDecimal(60);
		System.out.println(v1.divide(v2,2,RoundingMode.HALF_UP));
	}

}
