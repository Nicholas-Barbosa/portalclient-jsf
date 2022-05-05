package com.farawaybr.portal.microsoft.excel;

import java.util.List;

public class RowObject {

	private int offset;

	private List<? extends CellAttribute> cellAttributes;

	public RowObject(int offset) {
		super();
		this.offset = offset;
	}

	public RowObject(int offset, List<? extends CellAttribute> cellAttributes) {
		super();
		this.offset = offset;
		this.cellAttributes = cellAttributes;
	}

	public int getOffset() {
		return offset;
	}

	public List<? extends CellAttribute> getCellAttributes() {
		return cellAttributes;
	}

	@Override
	public String toString() {
		return "RowObject [offset=" + offset + ", cellAttributes=" + cellAttributes + "]";
	}

}
