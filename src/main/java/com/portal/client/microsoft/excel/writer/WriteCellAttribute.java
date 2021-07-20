package com.portal.client.microsoft.excel.writer;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.CellType;

public class WriteCellAttribute {

	private int cellPosition;
	private Object value;
	private CellType cellType;

	public WriteCellAttribute(int postion, Object value, CellType cellType) {
		super();
		this.cellPosition = postion;
		this.value = value;
		this.cellType = cellType;
	}

	public WriteCellAttribute(int postion, Object value) {
		super();
		this.cellPosition = postion;
		this.value = value;
		this.cellType = CellType.STRING;
	}

	public int getCellPosition() {
		return cellPosition;
	}

	public Object getValue() {
		return value;
	}

	public CellType getCellType() {
		return cellType;
	}

	public static class WriteCellAttributeBuilder {

		private static final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));

		static {
			numberFormat.setMaximumFractionDigits(3);
		}

		public static WriteCellAttribute of(int postion, Object value) {
			return new WriteCellAttribute(postion, value);
		}

		public static WriteCellAttribute of(int position, Object value, CellType cell) {
			return new WriteCellAttribute(position, value, cell);
		}

		public static List<WriteCellAttribute> of(int startPosition, Object... value) {
			final AtomicInteger cellPosition = new AtomicInteger(startPosition);
			return Arrays.stream(value)
					.map(v -> WriteCellAttributeBuilder.of(cellPosition.getAndIncrement(), v))
					.collect(CopyOnWriteArrayList::new, List::add, List::addAll);

		}


		public static List<WriteCellAttribute> ofNumber(Integer startPosition, Number... value) {
			final AtomicInteger cellPosition = new AtomicInteger(startPosition);
			return Arrays.stream(value).parallel()
					.map(v -> WriteCellAttributeBuilder.ofNumber(cellPosition.getAndIncrement(), v))
					.collect(CopyOnWriteArrayList::new, List::add, List::addAll);

		}

		public static List<WriteCellAttribute> ofNumber(Number... value) {
			return WriteCellAttributeBuilder.ofNumber(0, value);

		}

		public static WriteCellAttribute ofNumber(int position, Number value) {
			return new WriteCellAttribute(position, numberFormat.format(value));
		}
	}
}
