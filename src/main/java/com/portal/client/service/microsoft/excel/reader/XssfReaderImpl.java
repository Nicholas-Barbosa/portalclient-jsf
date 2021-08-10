package com.portal.client.service.microsoft.excel.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.portal.client.service.microsoft.excel.CellAttribute;
import com.portal.client.service.microsoft.excel.RowObject;

@ApplicationScoped
public class XssfReaderImpl implements XssfReader {

	@Override
	public List<RowObject> read(InputStream xlsxInputStream, int initialOffset, int endOffset) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(xlsxInputStream)) {
			Sheet datatypeSheet = workbook.getSheetAt(0);
			List<RowObject> rowObjects = new LinkedList<>();
			datatypeSheet.forEach(r -> {
				if (r.getRowNum() >= initialOffset
						&& r.getRowNum() <= (endOffset == 0 ? datatypeSheet.getLastRowNum() : endOffset)) {
					IntStream cells = IntStream.iterate(0, i -> i < r.getLastCellNum(), i -> i + 1);
					List<CellAttribute> cellAttributes = cells.parallel().mapToObj(i -> r.getCell(i))
							.filter(c -> c != null).map(c -> {
								return new CellAttribute(c.getColumnIndex(), this.getCellValue(c));
							}).collect(CopyOnWriteArrayList::new, List::add, List::addAll);
					rowObjects.add(new RowObject(r.getRowNum(), cellAttributes));
				}
			});
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
