package com.portal.client.dto.builder;

import java.math.BigDecimal;

import com.portal.client.vo.Product;

public interface ContractItemBudgetBuilder {

	public ContractItemBudgetBuilder withGlobalDiscount(BigDecimal value);

	public ContractItemBudgetBuilder withLineDiscount(BigDecimal value);

	public ContractItemBudgetBuilder withUnitStValue(BigDecimal value);

	public ContractItemBudgetBuilder withUnitGrossValue(BigDecimal value);

	public ContractItemBudgetBuilder withTotalStValue(BigDecimal value);

	public ContractItemBudgetBuilder withTotalValue(BigDecimal value);

	public ContractItemBudgetBuilder withTotalGrossValue(BigDecimal value);

	public ContractItemBudgetBuilder withQuantity(int value);

	public ContractItemBudgetBuilder withUnitValue(BigDecimal value);

	public ContractItemBudgetBuilder withProduct(Product value);

	public ContractItemBudgetBuilder withMultiple(Product value);

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
