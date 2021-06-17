package com.portal.java.dto;

import java.math.BigDecimal;

public class Item {

	private BigDecimal individualDiscount;
	private BigDecimal lineDiscount;
	private BigDecimal totalDiscount;
	private final Product product;
	private int quantity;
	private ItemPrice itemPrice;

	public Item(BigDecimal individualDiscount, BigDecimal lineDiscount, Product product, int quantity,
			ItemPrice itemPrice) {
		super();
		this.individualDiscount = individualDiscount;
		this.lineDiscount = lineDiscount;
		this.totalDiscount = individualDiscount.add(lineDiscount);
		this.product = product;
		this.quantity = quantity;
		this.itemPrice = itemPrice;
	}

	public Item() {
		this(BigDecimal.ZERO, BigDecimal.ZERO, null, 0, null);
	}

	public BigDecimal getIndividualDiscount() {
		return individualDiscount;
	}

	public void setIndividualDiscount(BigDecimal individualDiscount) {
		this.totalDiscount = this.totalDiscount.subtract(this.individualDiscount);
		this.individualDiscount = individualDiscount;
		this.totalDiscount = this.totalDiscount.add(individualDiscount);
	}

	public BigDecimal getLineDiscount() {
		return lineDiscount;
	}

	public void setLineDiscount(BigDecimal lineDiscount) {
		this.totalDiscount = this.totalDiscount.subtract(this.lineDiscount);
		this.lineDiscount = lineDiscount;
		this.totalDiscount = this.totalDiscount.add(lineDiscount);
	}

	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public ItemPrice getItemPrice() {
		return itemPrice;
	}

	public void addAnyDiscount(BigDecimal discount) {
		this.totalDiscount = totalDiscount.add(discount);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		Item other = (Item) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

}
