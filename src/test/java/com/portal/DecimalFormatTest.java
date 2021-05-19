package com.portal;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class DecimalFormatTest {

	@Test
	void test() {
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(90.99));
	}

}
