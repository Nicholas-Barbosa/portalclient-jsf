package com.portal.java.microsoft.excel;

import java.util.List;

public class RowObject {

	private int offset;

	private List<CellAttribute> cellAttributes;

	public RowObject() {
		// TODO Auto-generated constructor stub
	}

	public RowObject(int offset, List<CellAttribute> cellAttributes) {
		super();
		this.offset = offset;
		this.cellAttributes = cellAttributes;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public List<CellAttribute> getCellAttributes() {
		return cellAttributes;
	}

	public void setCellAttributes(List<CellAttribute> cellAttributes) {
		this.cellAttributes = cellAttributes;
	}

	@Override
	public String toString() {
		return "RowObject [offset=" + offset + ", cellAttributes=" + cellAttributes + "]";
	}
	
	
}
