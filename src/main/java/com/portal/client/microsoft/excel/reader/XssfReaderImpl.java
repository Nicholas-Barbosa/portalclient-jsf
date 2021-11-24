package com.portal.client.microsoft.excel.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.portal.client.microsoft.excel.CellAttribute;
import com.portal.client.microsoft.excel.RowObject;

@ApplicationScoped
public class XssfReaderImpl implements XssfReader {

	@Override
	public List<RowObject> read(InputStream xlsxInputStream, int initialOffset, int endOffset) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(xlsxInputStream)) {
			Sheet datatypeSheet = workbook.getSheetAt(0);
			endOffset = endOffset == 0 ? datatypeSheet.getLastRowNum() : endOffset;
			List<RowObject> rowObjects = IntStream.rangeClosed(initialOffset, endOffset).parallel()
					.mapToObj(datatypeSheet::getRow).filter(r -> r != null).map(row -> {
						IntStream cells = IntStream.range(0, row.getLastCellNum());
						List<CellAttribute> cellAttributes = cells.mapToObj(c -> row.getCell(c))
								.filter(x -> x != null).map(x -> {
									return new CellAttribute(x.getColumnIndex(), this.getCellValue(x),x.getCellType());
								}).collect(CopyOnWriteArrayList::new, List::add, List::addAll);
						return new RowObject(row.getRowNum(), cellAttributes);
					}).collect(CopyOnWriteArrayList::new, List::add, List::addAll);
			return rowObjects;
		}

	}

	@Override
	public List<RowObject> read(byte[] xlsxStreams, int initialOffset, int endOffset) throws IOException {
		return this.read(new ByteArrayInputStream(xlsxStreams), initialOffset, endOffset);
	}

	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case NUMERIC:
			return cell.getNumericCellValue();
		default:
			return cell.getStringCellValue();
		}
	}
}
