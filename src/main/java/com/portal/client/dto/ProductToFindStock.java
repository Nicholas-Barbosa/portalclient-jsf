package com.portal.client.dto;

import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Product;

public class ProductToFindStock {

	private String commercialCode;

	public static ProductToFindStock of(Product product) {
		return new ProductToFindStock(product.getCommercialCode());
	}

	public ProductToFindStock(String commercialCode) {
		super();
		this.commercialCode = commercialCode;
	}

	@JsonbProperty("product")
	public String getCommercialCode() {
		return commercialCode;
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
		ProductToFindStock other = (ProductToFindStock) obj;
		if (commercialCode == null) {
			if (other.commercialCode != null)
				return false;
		} else if (!commercialCode.equals(other.commercialCode))
			return false;
		return true;
	}

}
