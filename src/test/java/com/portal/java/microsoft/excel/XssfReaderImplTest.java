package com.portal.java.microsoft.excel;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.portal.client.service.microsoft.excel.CellAttribute;
import com.portal.client.service.microsoft.excel.RowObject;
import com.portal.client.service.microsoft.excel.reader.XssfReader;
import com.portal.client.service.microsoft.excel.reader.XssfReaderBuilder;

class XssfReaderImplTest {

	private final String excelFileName = "C:\\Users\\nicho\\OneDrive\\Documentos\\testing_excel-poi\\readingExcel.xlsx";

	@Test
	void test() throws IOException {
		XssfReader reader = XssfReaderBuilder.createReader();
		RowObject rowObject = new RowObject(1, List.of(new CellAttribute(1)));
		reader.read(rowObject, new FileInputStream(excelFileName));
		assertEquals("A1", rowObject.getCellAttributes().get(0).getValue());
	}

}
