package com.portal.service.restclient;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;

import com.portal.dto.GssResponseClientsDTO;

class SimpleRestClientTemplateTest {

	@Test
	void test() {
		RestClientTemplate restClient = new SimpleRestClientTemplate();
		System.out.println(restClient.getForEntity("http://192.168.0.246:8091/rest/clients",
				MediaType.APPLICATION_JSON_TYPE, GssResponseClientsDTO.class));
	}

}
