package com.portal.client.microsoft.excel;

public class CellAttribute {

	private int cellOffset;
	private Object value;

	public CellAttribute(int cellOffset, Object value) {
		super();
		this.cellOffset = cellOffset;
		this.value = value;
	}

	
	public CellAttribute(int cellOffset) {
		super();
		this.cellOffset = cellOffset;
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
