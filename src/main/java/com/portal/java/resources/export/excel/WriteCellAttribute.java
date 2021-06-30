package com.portal.java.resources.export.excel;

import org.apache.poi.ss.usermodel.CellType;

public class WriteCellAttribute {

	private short cellPosition;
	private Object value;
	private CellType cellType;

	public WriteCellAttribute(final int cellPosition, Object value, CellType cellType) {
		super();
		this.cellPosition = (short) cellPosition;
		this.value = value;
		this.cellType = cellType;
	}

	public WriteCellAttribute(Object value, CellType cellType) {
		super();
		this.value = value;
		this.cellType = cellType;
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
}
