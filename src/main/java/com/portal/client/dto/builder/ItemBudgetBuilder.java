package com.portal.client.dto.builder;

import java.math.BigDecimal;

import com.portal.client.dto.ItemBudget;
import com.portal.client.dto.ItemBudgetValue;
import com.portal.client.dto.ProductValue;
import com.portal.client.util.MathUtils;
import com.portal.client.vo.Product;

public class ItemBudgetBuilder {

	private BigDecimal budgetGlobalDiscount;
	private BigDecimal lineDiscount;
	private BigDecimal unitStValue;
	private BigDecimal unitValue;
	private BigDecimal unitGrossValue;
	private BigDecimal totalStValue;
	private BigDecimal totalValue;
	private BigDecimal totalGrossValue;
	private int quantity;
	private Product product;

	public static ItemBudgetBuilder getInstance() {
		return new ItemBudgetBuilder();
	}

	public static ItemBudget product(Product product) {
		ItemBudgetBuilder builder = new ItemBudgetBuilder();
		ProductValue productValue = product.getValue();
		return builder.withProduct(product).withQuantity(1).withUnitGrossValue(productValue.getUnitGrossValue())
				.withUnitStValue(productValue.getUnitStValue()).withUnitValue(productValue.getUnitValue())
				.withTotalGrossValue(
						MathUtils.calculateTotalValueOverQuantity(builder.getQuantity(), builder.getUnitGrossValue()))
				.withTotalStValue(
						MathUtils.calculateTotalValueOverQuantity(builder.getQuantity(), builder.getUnitStValue()))
				.withTotalValue(
						MathUtils.calculateTotalValueOverQuantity(builder.getQuantity(), builder.getUnitValue()))
				.withLineDiscount(BigDecimal.ZERO).withGlobalDiscount(BigDecimal.ZERO).build();
	}

	public ItemBudgetBuilder withGlobalDiscount(BigDecimal value) {
		this.budgetGlobalDiscount = value;
		return this;
	}

	public ItemBudgetBuilder withLineDiscount(BigDecimal value) {
		this.lineDiscount = value;
		return this;
	}

	public ItemBudgetBuilder withUnitStValue(BigDecimal value) {
		this.unitStValue = value;
		return this;
	}

	public ItemBudgetBuilder withUnitGrossValue(BigDecimal value) {
		this.unitGrossValue = value;
		return this;
	}

	public ItemBudgetBuilder withTotalStValue(BigDecimal value) {
		this.totalStValue = value;
		return this;
	}

	public ItemBudgetBuilder withTotalValue(BigDecimal value) {
		this.totalValue = value;
		return this;
	}

	public ItemBudgetBuilder withTotalGrossValue(BigDecimal value) {
		this.totalGrossValue = value;
		return this;
	}

	public ItemBudgetBuilder withQuantity(int value) {
		this.quantity = value;
		return this;
	}

	public ItemBudgetBuilder withUnitValue(BigDecimal value) {
		this.unitValue = value;
		return this;
	}

	public ItemBudgetBuilder withProduct(Product value) {
		this.product = value;
		return this;
	}

	public BigDecimal getBudgetGlobalDiscount() {
		return budgetGlobalDiscount;
	}

	public BigDecimal getLineDiscount() {
		return lineDiscount;
	}

	public BigDecimal getUnitStValue() {
		return unitStValue;
	}

	public BigDecimal getUnitValue() {
		return unitValue;
	}

	public BigDecimal getUnitGrossValue() {
		return unitGrossValue;
	}

	public BigDecimal getTotalStValue() {
		return totalStValue;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public BigDecimal getTotalGrossValue() {
		return totalGrossValue;
	}

	public int getQuantity() {
		return quantity;
	}

	public Product getProduct() {
		return product;
	}

	public ItemBudget build() {
		return new ItemBudget(product, new ItemBudgetValue(quantity, budgetGlobalDiscount, lineDiscount, unitStValue,
				unitValue, unitGrossValue, totalStValue, totalValue, totalGrossValue, product.getValue()));
	}

}
