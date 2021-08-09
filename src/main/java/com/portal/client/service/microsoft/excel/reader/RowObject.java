package com.portal.client.service.microsoft.excel.reader;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RowObject {

	private int offset;

	private List<CellAttribute> cellAttributes;

	public RowObject() {
		this.cellAttributes = new CopyOnWriteArrayList<>(cellAttributes);
	}

	public RowObject(int offset) {
		super();
		this.offset = offset;
		this.cellAttributes = new CopyOnWriteArrayList<>();
	}

	public RowObject(int offset, List<CellAttribute> cellAttributes) {
		super();
		this.offset = offset;
		this.cellAttributes = new CopyOnWriteArrayList<>(cellAttributes);
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

	public void addCellAttribute(CellAttribute cellAttribute) {
		this.cellAttributes.add(cellAttribute);
	}

	@Override
	public String toString() {
		return "RowObject [offset=" + offset + ", cellAttributes=" + cellAttributes + "]";
	}

}
