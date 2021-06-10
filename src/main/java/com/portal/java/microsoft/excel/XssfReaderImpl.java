package com.portal.java.microsoft.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XssfReaderImpl implements XssfReader {

	@Override
	public void read(RowObject rowObject, InputStream xlsxFile) {
		try (Workbook workbook = new XSSFWorkbook(xlsxFile)) {
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Row row = datatypeSheet.getRow(rowObject.getOffset());
			rowObject.getCellAttributes().parallelStream().forEach(c -> {
				Cell cell = row.getCell(c.getCellOffset());
				if (cell != null) {
					c.setValue(cell);
				}
			});
		} catch (IOException e) {
			// TODO: handle exception
		}

	}

}
