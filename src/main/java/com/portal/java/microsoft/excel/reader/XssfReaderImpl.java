package com.portal.java.microsoft.excel.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
	public Deque<RowObject> read(byte[] xlsxStreams) throws IOException {
		return this.read(new ByteArrayInputStream(xlsxStreams));
	}

	@Override
	public Deque<RowObject> read( InputStream xlsxFile) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(xlsxFile)) {
			Deque<RowObject> objectstToReturn = new LinkedList<>();
			Sheet datatypeSheet = workbook.getSheetAt(0);
			datatypeSheet.forEach(currentRow -> {
				RowObject rowObject = new RowObject(currentRow.getRowNum());
				objectstToReturn.offer(rowObject);
				IntStream.rangeClosed(currentRow.getFirstCellNum(), currentRow.getLastCellNum() - 1).filter(i -> i >= 0)
						.parallel().forEach(i -> {
							Cell currentCell = currentRow.getCell(i);
							if (currentCell != null) {
								addCellAttributeAccordingToType(rowObject, currentCell, currentCell.getColumnIndex());
							}
						});

			});
			return objectstToReturn;
		}

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

	private void addCellAttributeAccordingToType(RowObject rowObject, Cell currentCell, int i) {
		switch (currentCell.getCellType()) {
		case STRING:
			rowObject.addCellAttribute(new CellAttribute(i, currentCell.getStringCellValue()));
			break;
		case NUMERIC:
			rowObject.addCellAttribute(new CellAttribute(i, currentCell.getNumericCellValue()));
			break;
		case FORMULA:
			rowObject.addCellAttribute(new CellAttribute(i, currentCell.getCellFormula()));
			break;
		default:
			rowObject.addCellAttribute(new CellAttribute(i, currentCell));
			break;
		}
	}

}
