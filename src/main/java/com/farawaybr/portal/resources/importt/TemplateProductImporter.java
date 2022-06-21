package com.farawaybr.portal.resources.importt;

import java.io.IOException;

public interface TemplateProductImporter {

	default void execute(byte[] xlsxstreams, int codeColumn, int quantityColumn, String customerCode,
			String customerStore) throws IOException {
		this.extractData(xlsxstreams, codeColumn, quantityColumn, customerCode, customerStore);
		if (!isMismatchTypeCells()) {
			findProducts();
			
		}

	}

	public abstract void extractData(byte[] xlsxstreams, int codeColumn, int quantityColumn, String customerCode,
			String customerStore) throws IOException;

	public abstract void findProducts();

	default boolean isMismatchTypeCells() {
		return false;
	}

}
