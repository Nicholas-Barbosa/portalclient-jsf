package com.portal.client.service.microsoft.excel.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.portal.client.service.microsoft.excel.CellAttribute;
import com.portal.client.service.microsoft.excel.RowObject;

@ApplicationScoped
public class XssfReaderImpl implements XssfReader {

	@Override
	public List<RowObject> read(InputStream xlsxInputStream, short initialOffset, short endOffset) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(xlsxInputStream)) {
			Sheet datatypeSheet = workbook.getSheetAt(0);
			List<RowObject> rowObjects = new LinkedList<>();
			datatypeSheet.forEach(r -> {
				IntStream cells = IntStream.iterate(initialOffset,
						i -> i <= (endOffset == 0 ? r.getLastCellNum() - 1 : endOffset), i -> i + 1);
				List<CellAttribute> cellAttributes = cells.parallel().mapToObj(i -> r.getCell(i)).filter(c -> c != null)
						.map(c -> {
							return new CellAttribute(c.getColumnIndex(), c.getStringCellValue());
						}).collect(CopyOnWriteArrayList::new, List::add, List::addAll);
				rowObjects.add(new RowObject(r.getRowNum(), cellAttributes));
			});
			return rowObjects;
		}

	}

	@Override
	public List<RowObject> read(byte[] xlsxStreams, short initialOffset, short endOffset) throws IOException {
		return this.read(new ByteArrayInputStream(xlsxStreams), initialOffset, endOffset);
	}

}
