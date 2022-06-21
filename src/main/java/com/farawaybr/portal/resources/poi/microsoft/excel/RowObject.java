package com.farawaybr.portal.resources.poi.microsoft.excel;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RowObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int offset;

	private List<CellAttribute> cellAttributes;

	public RowObject(int offset, List<CellAttribute> cellAttributes) {
		super();
		this.offset = offset;
		this.cellAttributes = new CopyOnWriteArrayList<>();
		cellAttributes.parallelStream().forEach(this::addCell);
	}

	public int getOffset() {
		return offset;
	}

	public List<CellAttribute> getCellAttributes() {
		return cellAttributes;
	}

	public CellAttribute getCell(int cellOffset) {
		return cellAttributes.parallelStream().filter(cell -> cell.getCellOffset() == cellOffset).findAny()
				.orElse(null);
	}

	public void setCellValue(int cellOffset, Object value) {
		this.getCell(cellOffset).setValue(value);
	}

	public void addCell(CellAttribute cell) {
		cellAttributes.add(cell);
		cell.setRow(this);
	}

	public boolean removeCell(CellAttribute cell) {
		if (cellAttributes.remove(cell)) {
			cell.setRow(null);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "RowObject [offset=" + offset + ", cellAttributes=" + cellAttributes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + offset;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RowObject other = (RowObject) obj;
		if (offset != other.offset)
			return false;
		return true;
	}

}
