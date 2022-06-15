package com.farawaybr.portal.microsoft.excel.reader;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.farawaybr.portal.microsoft.excel.CellAttribute;

public enum CellReadPolicy {

	FROM_TO {
		@Override
		public List<CellAttribute> read(Row row, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return IntStream.rangeClosed(arg1, arg2).mapToObj(row::getCell).filter(cell -> cell != null)
					.map(cell -> new CellAttribute(cell.getColumnIndex(), ALL.getCellValue(cell), cell.getCellType()))
					.collect(Collectors.toList());
		}
	},
	ALL {
		@Override
		public List<CellAttribute> read(Row row, int arg1, int arg2) {
			return IntStream.range(0, row.getLastCellNum()).mapToObj(row::getCell)
					.map(cell -> new CellAttribute(cell.getColumnIndex(), ALL.getCellValue(cell), cell.getCellType()))
					.collect(Collectors.toList());
		}
	},
	FIRST_SECOND {
		@Override
		public List<CellAttribute> read(Row row, int arg1, int arg2) {
			Cell cellFromFirstColumn = row.getCell(arg1);
			Cell cellFromSecondColumn = row.getCell(arg2);
			return List.of(
					new CellAttribute(cellFromFirstColumn.getColumnIndex(),
							FIRST_SECOND.getCellValue(cellFromFirstColumn), cellFromFirstColumn.getCellType()),
					new CellAttribute(cellFromSecondColumn.getColumnIndex(),
							FIRST_SECOND.getCellValue(cellFromSecondColumn), cellFromSecondColumn.getCellType()));
		}
	};

	public abstract List<CellAttribute> read(Row row, int arg1, int arg2);

	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case NUMERIC:
			return cell.getNumericCellValue();
		default:
			return cell.getStringCellValue();
		}
	}

}
