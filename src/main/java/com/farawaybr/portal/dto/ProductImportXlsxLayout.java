package com.farawaybr.portal.dto;

import java.util.List;

import com.farawaybr.portal.resources.poi.microsoft.excel.RowObject;

public class ProductImportXlsxLayout {

	private int productColumnOffset, quantityColumnsOffset;
	private List<RowObject> rows;

	public List<RowObject> getRows() {
		return rows;
	}

	public void setRows(List<RowObject> rows) {
		this.rows = rows;
	}

	public int getProductColumnOffset() {
		return productColumnOffset;
	}

	public void setProductColumnOffset(int productColumnOffset) {
		this.productColumnOffset = productColumnOffset;
	}

	public int getQuantityColumnsOffset() {
		return quantityColumnsOffset;
	}

	public void setQuantityColumnsOffset(int quantityColumnsOffset) {
		this.quantityColumnsOffset = quantityColumnsOffset;
	}

}
