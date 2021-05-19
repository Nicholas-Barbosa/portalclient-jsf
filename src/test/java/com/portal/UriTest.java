package com.portal;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class UriTest {

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() {
		URI uri = URI.create("http://localhost:8080/portal/faces/budget.xhtml");
		assertEquals("/portal/faces/budget.xhtml", uri.getPath());
	}

}
