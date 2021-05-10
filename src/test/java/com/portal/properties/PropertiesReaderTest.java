package com.portal.properties;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PropertiesReaderTest {

	@Test
	void test() {
		assertEquals("http://192.168.0.201:8090/rest", new PropertiesReader().getProperty("orcamento_api_url_prod"));
	}

}
