package com.farawaybr.portal.dto;

import java.io.Serializable;

import com.farawaybr.portal.resources.poi.microsoft.excel.CellAttribute;

public class ProductRowExcelData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6363804532908463105L;
	private CellAttribute code;
	private CellAttribute quantity;
	private int excelRowIndex;

	public ProductRowExcelData() {
		// TODO Auto-generated constructor stub
	}

	public ProductRowExcelData(CellAttribute code, CellAttribute quantity, int excelRowIndex) {
		super();
		this.code = code;
		this.quantity = quantity;
		this.excelRowIndex = excelRowIndex;
	}

	public CellAttribute getCode() {
		return code;
	}

	public void setCode(CellAttribute code) {
		this.code = code;
	}

	public CellAttribute getQuantity() {
		return quantity;
	}

	public void setQuantity(CellAttribute quantity) {
		this.quantity = quantity;
	}

	public int getExcelRowIndex() {
		return excelRowIndex;
	}

	public void setExcelRowIndex(int excelRowIndex) {
		this.excelRowIndex = excelRowIndex;
	}

	public Object getQuantityValue() {
		return quantity.getValue();
	}

	public void setQuantityValue(Object value) {
		this.quantity.setValue(value);
	}

	@Override
	public String toString() {
		return "ProductImporterExtractedData [code=" + code + ", quantity=" + quantity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		ProductRowExcelData other = (ProductRowExcelData) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.getValue().equals(other.code.getValue()))
			return false;
		return true;
	}

}
