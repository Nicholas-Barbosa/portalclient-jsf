package com.portal.client.service.microsoft.excel.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.portal.client.service.microsoft.excel.CellAttribute;
import com.portal.client.service.microsoft.excel.RowObject;

@ApplicationScoped
public class XssfReaderImpl implements XssfReader {

	@Override
	public void read(RowObject rowObject, InputStream xlsxFile) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(xlsxFile)) {
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Row row = datatypeSheet.getRow(rowObject.getOffset() - 1);
			rowObject.getCellAttributes().parallelStream().forEach(c -> {
				Cell cell = row.getCell(c.getCellOffset() - 1);
				if (cell != null) {
					setCellAttributeValueAccordingToType(c, cell);
				}
			});
		}

	}

	@Override
	public void read(RowObject rowObject, byte[] xlsxStreams) throws IOException {
		this.read(rowObject, new ByteArrayInputStream(xlsxStreams));

	}

	@Override
	public void read(List<RowObject> rowObjects, InputStream xlsxInputStream) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(xlsxInputStream)) {
			Sheet datatypeSheet = workbook.getSheetAt(0);
			rowObjects.parallelStream().forEach(r -> {
				Row currentRow = datatypeSheet.getRow(r.getOffset());
				r.getCellAttributes().parallelStream().forEach(cellAttribute -> {
					Cell rawCell = currentRow.getCell(cellAttribute.getCellOffset());
					this.setCellAttributeValueAccordingToType(cellAttribute, rawCell);
				});
			});
		}

	}

	@Override
	public void read(List<RowObject> rowObjects, byte[] xlsxStreams) throws IOException {
		this.read(rowObjects, new ByteArrayInputStream(xlsxStreams));
	}

	private void setCellAttributeValueAccordingToType(CellAttribute attribute, Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			attribute.setValue(cell.getStringCellValue());
			break;
		case NUMERIC:
			attribute.setValue(cell.getNumericCellValue());
			break;
		case FORMULA:
			attribute.setValue(cell.getCellFormula());
			break;
		default:
			attribute.setValue(cell.toString());
			break;
		}
	}

}
