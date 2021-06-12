package com.portal.java.microsoft.excel;

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
			Row row = datatypeSheet.getRow(rowObject.getOffset());
			rowObject.getCellAttributes().parallelStream().forEach(c -> {
				Cell cell = row.getCell(c.getCellOffset());
				if (cell != null) {
					c.setValue(cell);
				}
			});
		}

	}

	@Override
	public Deque<RowObject> read(int fromIndex, int endIndex, InputStream xlsxFile) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(xlsxFile)) {
			Deque<RowObject> objectstToReturn = new LinkedList<>();
			Sheet datatypeSheet = workbook.getSheetAt(0);
			datatypeSheet.groupRow(--fromIndex, --endIndex);
			datatypeSheet.forEach(currentRow -> {
				RowObject rowObject = new RowObject(currentRow.getRowNum());
				objectstToReturn.offer(rowObject);
				IntStream.rangeClosed(currentRow.getFirstCellNum(), currentRow.getLastCellNum()).parallel()
						.forEach(i -> {
							Cell currentCell = currentRow.getCell(i);
							if (currentCell != null) {
								rowObject.addCellAttribute(new CellAttribute(i, currentCell));
							}
						});

			});

			return objectstToReturn;
		}

	}

}
