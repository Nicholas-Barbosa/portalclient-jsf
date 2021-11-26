package com.portal.client.vo;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.ProductImage.ImageInfoState;
import com.portal.client.vo.builder.ProductDiscountDataBuilder;
import com.portal.client.vo.builder.ProductTechDetailBuilder;

public class Product {

	private final String code;
	private final String commercialCode;
	private final String description;
	private final String line;
	private final String acronymLine;
	private Integer stock;
	private final Boolean commercialBlock;
	private ProductImage image;
	private final ProductPriceData priceData;
	private ProductTechDetail productTechDetail;
	private String link;

	@JsonbCreator
	public static Product ofJsonb(@JsonbProperty("application") String application, @JsonbProperty("code") String code,
			@JsonbProperty("description_product_type") String line, @JsonbProperty("product_type") String acronymLine,
			@JsonbProperty("multiple") int multiple, @JsonbProperty("commercial_block") String commercialBlock,
			@JsonbProperty("commercial_code") String cCode, @JsonbProperty("st_value") BigDecimal stValue,
			@JsonbProperty("unit_price") BigDecimal unitValue, @JsonbProperty("stock") int stock,
			@JsonbProperty("description") String description,
			@JsonbProperty("unit_gross_value") BigDecimal unitGrossValue) {
		ProductDiscountData discountData = ProductDiscountDataBuilder.getInstance().withUnitGrossValue(unitGrossValue)
				.withUnitStValue(stValue).withUnitValue(unitValue).withTotalValue(unitValue)
				.withTotalGrossValue(unitGrossValue).withTotalStValue(stValue).build();
		ProductPriceData price = new ProductPriceData(stValue, unitValue, unitGrossValue, 1, multiple, discountData);
		System.out.println("Application: " + application);
		return new Product(code, cCode, description, line, acronymLine, stock,
				commercialBlock == null ? null : commercialBlock.equalsIgnoreCase("nao") ? false : true, null, price,
				application != null && !application.isBlank()
						? ProductTechDetailBuilder.getInstance().withApplication(application).build()
						: null);
	}

	public Product(String code, String commercialCode, String description, String line, String acronymLine,
			Integer stock, Boolean commercialBlock, ProductImage image, ProductPriceData price,
			ProductTechDetail productTechDetail) {
		super();
		this.code = code;
		this.commercialCode = commercialCode;
		this.description = description;
		this.line = line;
		this.acronymLine = acronymLine;
		this.stock = stock;
		this.commercialBlock = commercialBlock;
		this.image = image;
		this.priceData = price;
		this.productTechDetail = productTechDetail;
	}

	public String getCode() {
		return code;
	}

	public String getCommercialCode() {
		return commercialCode;
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Boolean isCommercialBlock() {
		return commercialBlock;
	}

	public ProductImage getImage() {
		return image;
	}

	public byte[] getImageStreams() {
		return image == null ? new byte[0] : image.getImageStreams();
	}

	public ProductPriceData getPriceData() {
		return priceData;
	}

	public void setImage(ProductImage productImage) {
		this.image = productImage;
	}

	public void setImage(byte[] streams, ImageInfoState state) {
		if (image == null) {
			image = new ProductImage(streams, state);
			return;
		}
		image.setImageStreams(streams);
		image.setCurrentState(state);
	}

	public ProductTechDetail getProductTechDetail() {
		return productTechDetail;
	}

	public void setProductTechDetail(ProductTechDetail productTechDetail) {
		this.productTechDetail = productTechDetail;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
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
	public String toString() {
		return "Product [code=" + code + ", commercialCode=" + commercialCode + ", description=" + description
				+ ", line=" + line + ", acronymLine=" + acronymLine + ", stock=" + stock + ", commercialBlock="
				+ commercialBlock + ", image=" + image + ", priceData=" + priceData + ", productTechDetail="
				+ productTechDetail + ", link=" + link + "]";
	}

}
