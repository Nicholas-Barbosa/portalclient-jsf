package com.portal;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class TestPath {

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() {
		InputStream in = getClass().getResourceAsStream("/report/budgetEstimate.jasper");
		System.out.println(in);
	}

}
