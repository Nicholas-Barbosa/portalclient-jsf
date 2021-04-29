package com.portal.service.restclient;

import java.net.URL;

import org.junit.jupiter.api.Test;

class EncodeTest {

	private Integer page = 1;

	@Test
	void test() {
		String urls = "http://192.168.0.246:8091/rest/clients?page=" + page + "&pageSize=12";
		page++;
		boolean inLoop = true;
		try {
			URL url = new URL(urls);

			while (inLoop) {
				System.out.println("in loop");
				url.openConnection() .getContent();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println();
			inLoop = false;
		} catch (Exception e) {
			test();
		}

	}

}
