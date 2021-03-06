package com.farawaybr.portal.vo;

import java.io.Serializable;

import com.farawaybr.portal.vo.ProductImage.ImageInfoState;

public class Product implements Comparable<Product>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code, commercialCode, ncm, description, line, acronymLine, link;
	private Integer stock, quantityOnOrders;
	private Boolean commercialBlock;
	private ProductImage image;
	private ProductPriceData priceData;
	private ProductTechDetail productTechDetail;

	public Product(String code, String commercialCode, String ncm, String description, String line, String acronymLine,
			Integer stock, Integer quantityOnOrders, Boolean commercialBlock, ProductImage image,
			ProductPriceData price, ProductTechDetail productTechDetail) {
		super();
		this.code = code;
		this.commercialCode = commercialCode;
		this.ncm = ncm;
		this.description = description;
		this.line = line;
		this.acronymLine = acronymLine;
		this.stock = stock;
		this.quantityOnOrders = quantityOnOrders;
		this.commercialBlock = commercialBlock;
		this.image = image;
		this.priceData = price;
		this.productTechDetail = productTechDetail;
	}

	public Product(Product p) {
		this(p.code, p.commercialCode, p.ncm, p.description, p.line, p.acronymLine, p.stock, p.quantityOnOrders,
				p.commercialBlock, p.image != null ? new ProductImage(p.image) : null,
				p.priceData != null ? new ProductPriceData(p.priceData) : null,
				p.productTechDetail != null ? new ProductTechDetail(p.productTechDetail) : null);
	}

	public String getCode() {
		return code;
	}

	public String getCommercialCode() {
		return commercialCode;
	}

	public String getNcm() {
		return ncm;
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

	public Integer getQuantityOnOrders() {
		return quantityOnOrders;
	}

	public void setQuantityOnOrders(Integer quantityOnOrders) {
		this.quantityOnOrders = quantityOnOrders;
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
		} else if (!commercialCode.equalsIgnoreCase(other.commercialCode))
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

	@Override
	public int compareTo(Product o) {
		// TODO Auto-generated method stub
		return code.compareTo(o.code);
	}

}
