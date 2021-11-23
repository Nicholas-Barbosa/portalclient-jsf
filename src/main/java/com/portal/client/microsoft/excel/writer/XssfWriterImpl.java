package com.portal.client.microsoft.excel.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.portal.client.microsoft.excel.RowObject;

@ApplicationScoped
public class XssfWriterImpl implements XssfWriter {

	@Override
	public byte[] write(String sheetName, List<RowObject> rowObjects) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetName);
		rowObjects.forEach(rowObject -> {
			XSSFRow row = sheet.createRow(rowObject.getOffset());
			rowObject.getCellAttributes().stream().map(c -> (WriteCellAttribute) c)
					.forEach(w -> this.createCell(w, row));
		});

		try (OutputStream out = new ByteArrayOutputStream()) {
			workbook.write(out);
			workbook.close();
			return ((ByteArrayOutputStream) out).toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void createCell(WriteCellAttribute attribute, XSSFRow row) {
		Cell cell = row.createCell(attribute.getCellOffset());
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
