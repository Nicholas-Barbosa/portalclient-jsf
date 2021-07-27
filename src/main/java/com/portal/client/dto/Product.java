package com.portal.client.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class Product {

	private final String code;
	private final String commercialCode;
	private final String applicability;
	private final String description;
	private final String line;
	private final String acronymLine;
	private int stock;
	private final int multiple;
	private final boolean commercialBlock;
	private ProductImage image;
	private final ProductValue price;

	@JsonbCreator
	public static Product ofJsonb(@JsonbProperty("application") String application,
			@JsonbProperty("gross_price") BigDecimal grossPrice, @JsonbProperty("code") String code,
			@JsonbProperty("description_product_type") String line, @JsonbProperty("product_type") String acronymLine,
			@JsonbProperty("multiple") int multiple, @JsonbProperty("commercial_block") String commercialBlock,
			@JsonbProperty("commercial_code") String cCode, @JsonbProperty("st_value") BigDecimal stValue,
			@JsonbProperty("unit_price") BigDecimal unitValue, @JsonbProperty("stock") int stock,
			@JsonbProperty("description") String description,
			@JsonbProperty("unit_gross_value") BigDecimal unitGrossValue) {
		ProductValue price = new ProductValue(stValue, unitValue, unitGrossValue);
		return new Product(code, cCode, application, description, line, acronymLine, stock, multiple,
				commercialBlock.equalsIgnoreCase("Nao") ? false : true, null, price);
	}

	public Product(String code, String commercialCode, String applicability, String description, String line,
			String acronymLine, int stock, int multiple, boolean commercialBlock, ProductImage image,
			ProductValue price) {
		super();
		this.code = code;
		this.commercialCode = commercialCode;
		this.applicability = applicability;
		this.description = description;
		this.line = line;
		this.acronymLine = acronymLine;
		this.stock = stock;
		this.multiple = multiple;
		this.commercialBlock = commercialBlock;
		this.image = image;
		this.price = price;
	}

	public String getCode() {
		return code;
	}

	public String getCommercialCode() {
		return commercialCode;
	}

	public String getApplicability() {
		return applicability;
	}

	public String getDescription() {
		return description;
	}

	public String getLine() {
		return line;
	}

	public String getAcronymLine() {
		return acronymLine;
	}

	public int getStock() {
		return stock;
	}

	public int getMultiple() {
		return multiple;
	}

	public boolean isCommercialBlock() {
		return commercialBlock;
	}

	public ProductImage getImage() {
		return image;
	}

	public byte[] getImageStreams() {
		return image.getImageStreams();
	}
	public ProductValue getPrice() {
		return price;
	}

	public void setImage(ProductImage productImage) {
		this.image = productImage;
	}
	
	public void setImage(byte[]streams) {
		if(image==null) {
			image= new ProductImage(streams);
			return;
		}
		image.setImageStreams(streams);
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

	
}
