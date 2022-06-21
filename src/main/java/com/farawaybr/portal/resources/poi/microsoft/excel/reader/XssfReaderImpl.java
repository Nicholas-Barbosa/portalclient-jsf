package com.farawaybr.portal.resources.poi.microsoft.excel.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.farawaybr.portal.resources.poi.microsoft.excel.RowObject;

@ApplicationScoped
public class XssfReaderImpl implements XssfReader {

	@Override
	public List<RowObject> read(InputStream xlsxInputStream, int arg1, int arg2, CellReadPolicy readPolicy, int fromRow,
			int toRow) throws IOException {
		try (Workbook workbook = new XSSFWorkbook(xlsxInputStream)) {
			Sheet datatypeSheet = workbook.getSheetAt(0);
			List<RowObject> rowObjects = IntStream
					.rangeClosed(fromRow, toRow == -1 ? datatypeSheet.getLastRowNum() : toRow).parallel()
					.mapToObj(datatypeSheet::getRow).filter(r -> r != null).map(row -> {
						return new RowObject(row.getRowNum(), readPolicy.read(row, arg1, arg2));
					}).collect(CopyOnWriteArrayList::new, List::add, List::addAll);
			return rowObjects;
		}

	}

	@Override
	public List<RowObject> read(byte[] xlsxStreams, int arg1, int arg2, CellReadPolicy readPolicy, int fromRow,
			int toRow) throws IOException {
		return this.read(new ByteArrayInputStream(xlsxStreams), arg1, arg2, readPolicy, fromRow, toRow);
	}

}
