package com.portal.jse;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class TestStream {

	@Test
	void test() {
		IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
			System.out.println("Lendo row " + i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	
}
