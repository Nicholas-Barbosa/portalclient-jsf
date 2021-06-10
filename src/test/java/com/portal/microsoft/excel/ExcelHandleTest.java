package com.portal.microsoft.excel;

import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

class ExcelHandleTest {

	private final String excelFileName = "C:\\Users\\nicho\\OneDrive\\Documentos\\testing_excel-poi\\readingExcel.xlsx";

	@Test
	private void test() {
		try (Workbook workbook = new XSSFWorkbook(excelFileName)) {
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					System.out.println(currentCell);
					System.out.println(currentCell.getColumnIndex());
				}
			}
			System.out.println();
		} catch (IOException e) {
			// TODO: handle exception
		}

	}

}
