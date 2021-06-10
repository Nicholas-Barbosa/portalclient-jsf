package com.portal.java.microsoft.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

class XssfReaderImplTest {

	@Test
	void test() throws FileNotFoundException {
		XssfReader reader = XssfReaderBuilder.createReader();
		RowObject rowObject = new RowObject(4, List.of(new CellAttribute(0)));
		reader.read(rowObject,
				new FileInputStream("C:\\Users\\nicho\\OneDrive\\Documentos\\testing_excel-poi\\readingExcel.xlsx"));
		System.out.println(rowObject);
	}

}
