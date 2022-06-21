package com.portal.java.microsoft.excel;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.farawaybr.portal.resources.poi.microsoft.excel.reader.XssfReader;
import com.farawaybr.portal.resources.poi.microsoft.excel.reader.XssfReaderBuilder;

class XssfReaderImplTest {

	private final String excelFileName = "C:\\Users\\nicho\\OneDrive\\Documentos\\testing_excel-poi\\readingExcel.xlsx";

	@Test
	void test() throws IOException {
		XssfReader reader = XssfReaderBuilder.createReader();
		assertEquals(363, reader.read(new FileInputStream(excelFileName), 0, 0).size());

	}

	@Test
	void tes2() throws IOException {
		XssfReader reader = XssfReaderBuilder.createReader();
		assertEquals("A363",
				reader.read(new FileInputStream(excelFileName), 0, 0).get(362).getCellAttributes().get(0).getValue());
	}
}
