package com.portal.client.microsoft.excel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.DoubleStream;

import org.apache.poi.ss.usermodel.CellType;

public class CellAttribute {

	private int cellOffset;
	private Object value;
	private CellType cellType;

	public CellAttribute(int cellOffset, Object value, CellType type) {
		super();
		this.cellOffset = cellOffset;
		this.value = value;
		this.cellType = type;
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

	public CellType getCellType() {
		return cellType;
	}

	@Override
	public String toString() {
		return "CellAttribute [cellOffset=" + cellOffset + ", value=" + value + "]";
	}

	public static class CellAttributeBuilder {

		public static CellAttribute of(int position, Object value, CellType cell) {
			return new CellAttribute(position, value, cell);
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
