package com.portal.java.dto;

import java.math.BigDecimal;

public class Item {

	private BigDecimal budgetGlobalDiscount;
	private BigDecimal lineDiscount;
	private BigDecimal totalDiscount;
	private final Product product;
	private int quantity;
	private ItemPrice itemPrice;

	public Item(BigDecimal budgetGlobalDiscount, BigDecimal lineDiscount, Product product, int quantity,
			ItemPrice itemPrice) {
		super();
		this.budgetGlobalDiscount = budgetGlobalDiscount;
		this.lineDiscount = lineDiscount;
		this.totalDiscount = budgetGlobalDiscount.add(lineDiscount);
		this.product = product;
		this.quantity = quantity;
		this.itemPrice = itemPrice;
	}

	public Item() {
		this(BigDecimal.ZERO, BigDecimal.ZERO, null, 0, null);
	}

	public BigDecimal getBudgetGlobalDiscount() {
		return budgetGlobalDiscount;
	}

	public void setBudgetGlobalDiscount(BigDecimal individualDiscount) {
		this.totalDiscount = this.totalDiscount.subtract(this.budgetGlobalDiscount);
		this.budgetGlobalDiscount = individualDiscount;
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

	public String line() {
		return product.getDescriptionType();
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
