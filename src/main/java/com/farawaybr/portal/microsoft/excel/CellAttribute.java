package com.farawaybr.portal.microsoft.excel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.DoubleStream;

import org.apache.poi.ss.usermodel.CellType;

public class CellAttribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cellOffset;
	private Object value;
	private CellType cellType;
	private RowObject row;

	public CellAttribute(int cellOffset, RowObject row, Object value, CellType type) {
		super();
		this.cellOffset = cellOffset;
		this.row = row;
		this.value = value;
		this.cellType = type;
	}

	public CellAttribute(int cellOffset, Object value, CellType cellType) {
		super();
		this.cellOffset = cellOffset;
		this.value = value;
		this.cellType = cellType;
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

	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

	public RowObject getRow() {
		return row;
	}

	public void setRow(RowObject row) {
		this.row = row;
	}

	public boolean autoRemove() {
		return this.row.removeCell(this);
	}

	public int getRowOffset() {
		// TODO Auto-generated method stub
		return this.row.getOffset();
	}

	@Override
	public String toString() {
		return "CellAttribute [cellOffset=" + cellOffset + ", value=" + value + ", type=" + cellType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cellOffset;
		result = prime * result + ((row == null) ? 0 : row.hashCode());
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
		CellAttribute other = (CellAttribute) obj;
		if (cellOffset != other.cellOffset)
			return false;
		if (row == null) {
			if (other.row != null)
				return false;
		} else if (!row.equals(other.row))
			return false;
		return true;
	}

	public static class CellAttributeBuilder {

		public static CellAttribute of(int position, RowObject row, Object value, CellType cell) {
			return new CellAttribute(position, row, value, cell);
		}

		public static CellAttribute of(int position, Object value, CellType cell) {
			return new CellAttribute(position, null, value, cell);
		}

		public static List<CellAttribute> of(int startPosition, Object... value) {
			final AtomicInteger cellPosition = new AtomicInteger(startPosition);
			return Arrays.stream(value)
					.map(v -> CellAttributeBuilder.of(cellPosition.getAndIncrement(), v, CellType.STRING))
					.collect(CopyOnWriteArrayList::new, List::add, List::addAll);

		}

		public static List<CellAttribute> ofNumber(Integer startPosition, double... values) {
			final AtomicInteger cellPosition = new AtomicInteger(startPosition);
			return DoubleStream.of(values).parallel()
					.mapToObj(v -> CellAttributeBuilder.ofNumber(cellPosition.getAndIncrement(), v))
					.collect(CopyOnWriteArrayList::new, List::add, List::addAll);

		}

		public static List<CellAttribute> ofNumber(double... value) {
			return CellAttributeBuilder.ofNumber(0, value);

		}

		public static CellAttribute ofNumber(int position, double value) {
			return new CellAttribute(position, value, CellType.NUMERIC);
		}
	}

}
