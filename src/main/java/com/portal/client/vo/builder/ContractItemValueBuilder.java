package com.portal.client.vo.builder;

import java.math.BigDecimal;

import com.portal.client.vo.ItemValue;

public interface ContractItemValueBuilder {

	ContractItemValueBuilder withLineDiscount(BigDecimal value);

	ContractItemValueBuilder withGlobalDiscount(BigDecimal value);

	ContractItemValueBuilder withUnitStValue(BigDecimal value);

	ContractItemValueBuilder withUnitGrossValue(BigDecimal value);

	ContractItemValueBuilder withTotalStValue(BigDecimal value);

	ContractItemValueBuilder withTotalValue(BigDecimal value);

	ContractItemValueBuilder withTotalGrossValue(BigDecimal value);

	ContractItemValueBuilder withQuantity(int value);

	ContractItemValueBuilder withMultiple(Integer value);
	
	ContractItemValueBuilder withUnitValue(BigDecimal value);

	ItemValue build();
}
