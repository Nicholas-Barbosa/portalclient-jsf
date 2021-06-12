package com.portal.java.microsoft.excel;

import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class RowObject {

	private int offset;

	private Deque<CellAttribute> cellAttributes;

	public RowObject() {
		this.cellAttributes = new ConcurrentLinkedDeque<>(cellAttributes);
	}

	public RowObject(int offset) {
		super();
		this.offset = offset;
		this.cellAttributes = new ConcurrentLinkedDeque<>();
	}

	public RowObject(int offset, List<CellAttribute> cellAttributes) {
		super();
		this.offset = offset;
		this.cellAttributes = new ConcurrentLinkedDeque<>(cellAttributes);
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public Queue<CellAttribute> getCellAttributes() {
		return cellAttributes;
	}

	public void setCellAttributes(Deque<CellAttribute> cellAttributes) {
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
