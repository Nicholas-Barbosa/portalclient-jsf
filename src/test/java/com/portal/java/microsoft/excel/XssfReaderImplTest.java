package com.portal.java.microsoft.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

class XssfReaderImplTest {

	private final String excelFileName = "C:\\Users\\nicho\\OneDrive\\Documentos\\testing_excel-poi\\readingExcel.xlsx";

	@Test
	void test() throws IOException {
		XssfReader reader = XssfReaderBuilder.createReader();
		RowObject rowObject = new RowObject(4, List.of(new CellAttribute(0)));
		reader.read(rowObject, new FileInputStream(excelFileName));
		System.out.println(rowObject);
	}

	@Test
	void testList() throws IOException {
		XssfReader reader = XssfReaderBuilder.createReader();
		reader.read(1, 363, new FileInputStream(excelFileName));

	}
}
