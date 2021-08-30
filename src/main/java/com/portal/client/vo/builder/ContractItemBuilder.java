package com.portal.client.vo.builder;

import java.math.BigDecimal;

import com.portal.client.vo.Product;

public interface ContractItemBuilder {

	public ContractItemBuilder withGlobalDiscount(BigDecimal value);

	public ContractItemBuilder withLineDiscount(BigDecimal value);

	public ContractItemBuilder withUnitStValue(BigDecimal value);

	public ContractItemBuilder withUnitGrossValue(BigDecimal value);

	public ContractItemBuilder withTotalStValue(BigDecimal value);

	public ContractItemBuilder withTotalValue(BigDecimal value);

	public ContractItemBuilder withTotalGrossValue(BigDecimal value);

	public ContractItemBuilder withQuantity(int value);

	public ContractItemBuilder withUnitValue(BigDecimal value);

	public ContractItemBuilder withProduct(Product value);

	public ContractItemBuilder withMultiple(Product value);

	public BigDecimal getBudgetGlobalDiscount();

	public BigDecimal getLineDiscount();

	public BigDecimal getUnitStValue();

	public BigDecimal getUnitValue();

	public BigDecimal getUnitGrossValue();

	public BigDecimal getTotalStValue();

	public BigDecimal getTotalValue();

	public BigDecimal getTotalGrossValue();
	
	public int getQuantity();
	
	public Integer getMultiple();

}
