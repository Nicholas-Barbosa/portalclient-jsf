package com.farawaybr.portal.service.excelimporter;

import java.io.IOException;

public interface TemplateProductImporter{

	default void execute(byte[] xlsxstreams, int codeColumn, int quantityColumn) throws IOException {
		this.extractResData(xlsxstreams, codeColumn, quantityColumn);

		if (!isMismatchTypeCells()) {
			findProducts();
			if (!isMismatchProductsMultiple()) {
			}
		}

	}

	public abstract void extractResData(byte[] xlsxstreams, int codeColumn, int quantityColumn) throws IOException;

	public abstract void findProducts();

	default boolean isMismatchTypeCells() {
		return false;
	}

	default boolean isMismatchProductsMultiple() {
		return false;
	}
}
