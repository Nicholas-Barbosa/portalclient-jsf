package com.portal.jse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;

import org.junit.jupiter.api.Test;

class TestInflate {

	@Test
	void test() {
		try (InputStream file = new BufferedInputStream(
				new FileInputStream("C:\\Users\\nicho\\Downloads\\imagens_tratadas\\imagens_tratadas\\AX001.JPG"));
				OutputStream out = new BufferedOutputStream(
						new FileOutputStream("C:\\Users\\nicho\\Downloads\\imagens_tratadas\\001.JPG"))) {
			Deflater def = new Deflater();
			def.setInput(file.readAllBytes());
			byte[] deflated = new byte[524288];
			def.finish();
			def.deflate(deflated);
			out.write(deflated);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
