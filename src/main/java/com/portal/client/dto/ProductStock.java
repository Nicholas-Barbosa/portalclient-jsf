package com.portal.client.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ProductStock {

	private String commercialCode;
	private int stock;

	@JsonbCreator
	public ProductStock(@JsonbProperty("product") String commercialCode, @JsonbProperty("available") int stock) {
		super();
		this.commercialCode = commercialCode;
		this.stock = stock;
	}

	public String getCommercialCode() {
		return commercialCode;
	}

	public int getStock() {
		return stock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commercialCode == null) ? 0 : commercialCode.hashCode());
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
		ProductStock other = (ProductStock) obj;
		if (commercialCode == null) {
			if (other.commercialCode != null)
				return false;
		} else if (!commercialCode.equals(other.commercialCode))
			return false;
		return true;
	}

	
	
}
