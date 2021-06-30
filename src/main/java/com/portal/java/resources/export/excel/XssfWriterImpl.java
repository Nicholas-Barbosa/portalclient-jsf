package com.portal.java.resources.export.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XssfWriterImpl implements XssfWriter {

	@Override
	public byte[] write(List<WriteRowObject> rowObjects) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("conferência-cálculos");
		int rowNum = 0;
		System.out.println("rows to write " +rowObjects.size());
		for (WriteRowObject rowObject : rowObjects) {
			XSSFRow row = sheet.createRow(rowNum++);
			int cellNum = 0;
			for (WriteCellAttribute attribute : rowObject.getAttributes()) {
				Cell cell = row.createCell(cellNum++);
				switch (attribute.getCellType()) {
				case NUMERIC:
					cell.setCellValue((Double) attribute.getValue());
					break;
				default:
					cell.setCellValue(attribute.getValue() + "");
					break;
				}
			}
		}
		try {
			OutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			workbook.close();
			return ((ByteArrayOutputStream) out).toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
