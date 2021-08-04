package com.portal.java.microsoft.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.portal.client.service.microsoft.excel.reader.CellAttribute;
import com.portal.client.service.microsoft.excel.reader.RowObject;
import com.portal.client.service.microsoft.excel.reader.XssfReader;
import com.portal.client.service.microsoft.excel.reader.XssfReaderBuilder;

class XssfReaderImplTest {

	private final String excelFileName = "C:\\Users\\nicho\\OneDrive\\Documentos\\testing_excel-poi\\readingExcel.xlsx";

	@Test
	void test() throws IOException {
		XssfReader reader = XssfReaderBuilder.createReader();
		RowObject rowObject = new RowObject(1, List.of(new CellAttribute(1)));
		reader.read(rowObject, new FileInputStream(excelFileName));
	}

	@Test
	void testList() throws IOException {
		XssfReader reader = XssfReaderBuilder.createReader();
		System.out.println("Deque " + reader.read(new FileInputStream(excelFileName)));

	}
}
