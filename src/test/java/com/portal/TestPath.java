package com.portal;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class TestPath {

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() throws URISyntaxException {
		System.out.println(getClass().getResource("/report/images/gauss.png").toURI().getPath());
	}

}
