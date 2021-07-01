package com.portal.java.microsoft.excel.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@ApplicationScoped
public class XssfWriterImpl implements XssfWriter {

	@Override
	public byte[] write(List<WriteRowObject> rowObjects) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("conferência-cálculos");
		rowObjects.forEach(rowObject -> {
			XSSFRow row = sheet.createRow(rowObject.getRowPosition());
			rowObject.getAttributes().parallelStream().forEach(w -> this.createCell(w, row));
		});

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

	private void createCell(WriteCellAttribute attribute, XSSFRow row) {
		Cell cell = row.createCell(attribute.getCellPosition());
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
