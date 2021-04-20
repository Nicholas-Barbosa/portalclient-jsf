package com.portal.service.restclient;

import org.junit.jupiter.api.Test;

import com.portal.client.rest.RestClient;
import com.portal.client.rest.SimpleRestClient;
import com.portal.dto.GssResponseClientsDTO;

class SimpleRestClientTemplateTest {

	@Test
	void test() {
		RestClient restClient = new SimpleRestClient();
		System.out.println(
				restClient.getForEntity("http://192.168.0.246:8091/rest/clients", GssResponseClientsDTO.class));
	}

}
