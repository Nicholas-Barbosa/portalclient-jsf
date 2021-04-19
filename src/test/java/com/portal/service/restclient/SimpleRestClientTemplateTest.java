package com.portal.service.restclient;

import org.junit.jupiter.api.Test;

import com.portal.client.rest.RestClientTemplate;
import com.portal.client.rest.SimpleRestClientTemplate;
import com.portal.dto.GssResponseClientsDTO;

class SimpleRestClientTemplateTest {

	@Test
	void test() {
		RestClientTemplate restClient = new SimpleRestClientTemplate();
		System.out.println(
				restClient.getForEntity("http://192.168.0.246:8091/rest/clients", GssResponseClientsDTO.class));
	}

}
