package com.portal.jse;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class PathTest {

	

	@Test
	void test() {
		Path path1 = Path.of("imagens_tratadas/AX001.JPG");
		Path path2 =Path.of("AX001.JPG");
		System.out.println(path1.relativize(path2).normalize());
	}

}
