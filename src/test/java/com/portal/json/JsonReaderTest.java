package com.portal.json;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.Test;

import com.portal.dto.CustomerPageDTO;

class JsonReaderTest {

	@Test
	void test() {

		Jsonb json = JsonbBuilder.create();
		String jsonSt = "{\r\n" + "    \"total_items\": 162,\r\n" + "    \"client\": [\r\n" + "        {\r\n"
				+ "            \"address\": \"R JOSE DA SILVA LUCENA 387\",\r\n"
				+ "            \"code\": \"000008\",\r\n" + "            \"store\": \"01\",\r\n"
				+ "            \"state\": \"PE\",\r\n" + "            \"table\": \"186\",\r\n"
				+ "            \"cgc\": \"07214441000135\",\r\n" + "            \"blocked\": \"Sim\",\r\n"
				+ "            \"name\": \"A L DISTRIBUIDORA DE PECAS LTDA\",\r\n"
				+ "            \"fantasy_name\": \"ALVES ATACADO\",\r\n" + "            \"city\": \"RECIFE\"\r\n"
				+ "        }\r\n" + "    ],\r\n" + "    \"total_page\": 162,\r\n" + "    \"page_size\": 1,\r\n"
				+ "    \"page\": 1\r\n" + "}";
		System.out.println(json.fromJson(jsonSt, CustomerPageDTO.class));
	}

}
