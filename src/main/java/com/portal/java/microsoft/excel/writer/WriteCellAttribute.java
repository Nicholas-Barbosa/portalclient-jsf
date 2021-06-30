package com.portal.java.microsoft.excel.writer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.poi.ss.usermodel.CellType;

public class WriteCellAttribute {

	private short cellPosition;
	private Object value;
	private CellType cellType;

	public WriteCellAttribute(Object value, CellType cellType) {
		super();
		this.value = value;
		this.cellType = cellType;
	}

	public WriteCellAttribute(Object value) {
		super();
		this.value = value;
		this.cellType = CellType.STRING;
	}

	public short getCellPosition() {
		return cellPosition;
	}

	public Object getValue() {
		return value;
	}

	public CellType getCellType() {
		return cellType;
	}

	public static class WriteCellAttributeBuilder {
		public static WriteCellAttribute of(Object value) {
			return new WriteCellAttribute(value);
		}

		public static WriteCellAttribute of(Object value, CellType cell) {
			return new WriteCellAttribute(value, cell);
		}

		public static List<WriteCellAttribute> of(Object... value) {
			return Arrays.stream(value).parallel().map(WriteCellAttributeBuilder::of).collect(CopyOnWriteArrayList::new,
					List::add, List::addAll);

		}
	}
}
