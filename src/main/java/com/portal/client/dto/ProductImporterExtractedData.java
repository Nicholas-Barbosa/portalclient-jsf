package com.portal.client.dto;

import java.io.Serializable;

public class ProductImporterExtractedData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6363804532908463105L;
	private String code;
	private int quantity;

	public ProductImporterExtractedData() {
		// TODO Auto-generated constructor stub
	}

	public ProductImporterExtractedData(String code, int quantity) {
		super();
		this.code = code;
		this.quantity = quantity;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
		ProductImporterExtractedData other = (ProductImporterExtractedData) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

}
