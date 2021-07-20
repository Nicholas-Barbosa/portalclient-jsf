package com.portal.client.microsoft.excel.reader;

public class CellAttribute {

	private int cellOffset;
	private Object value;

	public CellAttribute() {
		// TODO Auto-generated constructor stub
	}

	public CellAttribute(int cellOffset) {
		super();
		this.cellOffset = cellOffset;
	}

	public CellAttribute(int cellOffset, Object value) {
		super();
		this.cellOffset = cellOffset;
		this.value = value;
	}

	public int getCellOffset() {
		return cellOffset;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CellAttribute [cellOffset=" + cellOffset + ", value=" + value + "]";
	}

}
