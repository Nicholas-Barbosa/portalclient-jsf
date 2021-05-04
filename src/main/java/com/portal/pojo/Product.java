package com.portal.pojo;

import java.io.Serializable;

public class Product implements Serializable, Comparable<Product> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1073082288183838723L;

	private String code;

	private String descriptionType;

	private String commercialCode;

	private String type;

	private String description;

	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(String code, String descriptionType, String commercialCode, String type, String description) {
		super();
		this.code = code;
		this.descriptionType = descriptionType;
		this.commercialCode = commercialCode;
		this.type = type;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescriptionType() {
		return descriptionType;
	}

	public String getCommercialCode() {
		return commercialCode;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
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
		Product other = (Product) obj;
		if (commercialCode == null) {
			if (other.commercialCode != null)
				return false;
		} else if (!commercialCode.equals(other.commercialCode))
			return false;
		return true;
	}

	@Override
	public int compareTo(Product o) {
		// TODO Auto-generated method stub
		return commercialCode.compareTo(o.commercialCode);
	}

}
