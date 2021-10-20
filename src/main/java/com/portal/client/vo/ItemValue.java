package com.portal.client.vo;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.portal.client.dto.ProductValue;
import com.portal.client.util.MathUtils;

public class ItemValue extends ProductValue {

	private BigDecimal budgetGlobalDiscount;
	private BigDecimal lineDiscount;
	private BigDecimal totalDiscount;

	private final Map<String, BigDecimal> valuesWithNoDisc = new ConcurrentHashMap<>();

	public ItemValue(ProductValue pValue) {
		super(pValue);
		this.setValuesWithoutDiscounts(pValue.getUnitStValue(), pValue.getUnitValue(), pValue.getUnitGrossValue());
		this.totalDiscount = BigDecimal.ZERO;
		this.budgetGlobalDiscount = BigDecimal.ZERO;
		this.lineDiscount = BigDecimal.ZERO;
	}

	public ItemValue(int quantity, Integer multiple, BigDecimal budgetGlobalDiscount, BigDecimal lineDiscount,
			BigDecimal unitStValue, BigDecimal unitValue, BigDecimal unitGrossValue, BigDecimal totalStValue,
			BigDecimal totalValue, BigDecimal totalGrossValue) {
		super(unitStValue, unitValue, unitGrossValue, totalStValue, totalValue, totalGrossValue, quantity, multiple);
		this.budgetGlobalDiscount = budgetGlobalDiscount;
		this.lineDiscount = lineDiscount;
		this.totalDiscount = lineDiscount == null ? this.budgetGlobalDiscount
				: this.budgetGlobalDiscount.add(this.lineDiscount);

		this.getValuesWithoutDiscount();

	}

	private final void getValuesWithoutDiscount() {
		if (budgetGlobalDiscount == null || budgetGlobalDiscount.equals(BigDecimal.ZERO) && lineDiscount == null
				|| lineDiscount.equals(BigDecimal.ZERO)) {
			this.setValuesWithoutDiscounts(super.getUnitStValue(), super.getUnitValue(), super.getUnitGrossValue());
		} else {
			if (!budgetGlobalDiscount.equals(BigDecimal.ZERO)) {
				BigDecimal unitValue = MathUtils.subtractValueByPercentage(budgetGlobalDiscount, super.getUnitValue());
				BigDecimal unitStValue = MathUtils.subtractValueByPercentage(budgetGlobalDiscount,
						super.getUnitStValue());
				BigDecimal unitGrossValue = MathUtils.subtractValueByPercentage(budgetGlobalDiscount,
						super.getUnitGrossValue());
				this.setValuesWithoutDiscounts(unitStValue, unitValue, unitGrossValue);

			} else {
				BigDecimal unitStValue = MathUtils.subtractValueByPercentage(lineDiscount, super.getUnitStValue());
				BigDecimal unitValue = MathUtils.subtractValueByPercentage(lineDiscount, super.getUnitValue());
				BigDecimal unitGrossValue = MathUtils.subtractValueByPercentage(lineDiscount,
						super.getUnitGrossValue());
				this.setValuesWithoutDiscounts(unitStValue, unitValue, unitGrossValue);
			}
			if (!lineDiscount.equals(BigDecimal.ZERO)) {
				BigDecimal unitValue = MathUtils.subtractValueByPercentage(lineDiscount,
						valuesWithNoDisc.get("unitValue"));
				BigDecimal unitStValue = MathUtils.subtractValueByPercentage(lineDiscount,
						valuesWithNoDisc.get("unitStValue"));
				BigDecimal unitGrossValue = MathUtils.subtractValueByPercentage(lineDiscount,
						valuesWithNoDisc.get("unitGrossValue"));
				setValuesWithoutDiscounts(unitStValue, unitValue, unitGrossValue);

			}

		}
	}

	private final void setValuesWithoutDiscounts(BigDecimal unitStValue, BigDecimal unitValue,
			BigDecimal unitGrossValue) {
		valuesWithNoDisc.put("unitStValue", unitStValue);
		valuesWithNoDisc.put("unitValue", unitValue);
		valuesWithNoDisc.put("unitGrossValue", unitGrossValue);
	}

	public BigDecimal getBudgetGlobalDiscount() {
		return budgetGlobalDiscount;
	}

	public void setBudgetGlobalDiscount(BigDecimal discount) {
		this.totalDiscount = this.totalDiscount.subtract(this.budgetGlobalDiscount);
		this.budgetGlobalDiscount = discount;
		this.totalDiscount = this.totalDiscount.add(discount);
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

	public void setTotalDiscount(BigDecimal totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public BigDecimal getUnitStValueWithoutDiscount() {
		return valuesWithNoDisc.get("unitStValue");
	}

	public BigDecimal getUnitValueWithoutDiscount() {
		return valuesWithNoDisc.get("unitValue");
	}

	public BigDecimal getUnitGrossValueWithoutDiscount() {
		return valuesWithNoDisc.get("unitGrossValue");
	}

	public BigDecimal getUnitStValueFromGBDiscount() {
		BigDecimal value = valuesWithNoDisc.get("unitStValueFromGBDiscount");
		return value == null ? getUnitGrossValueWithoutDiscount() : value;
	}

	public BigDecimal getUnitValueFromGBDiscount() {
		BigDecimal value = valuesWithNoDisc.get("unitValueFromGBDiscount");
		return value == null ? getUnitGrossValueWithoutDiscount() : value;
	}

	public BigDecimal getUnitGrossValueFromGBDiscount() {
		BigDecimal value = valuesWithNoDisc.get("unitGrossValueFromGBDiscount");
		return value == null ? getUnitGrossValueWithoutDiscount() : value;
	}

	public BigDecimal setUnitStValueFromGBDiscount(BigDecimal value) {
		return valuesWithNoDisc.put("unitStValueFromGBDiscount", value);
	}

	public BigDecimal setUnitValueFromGBDiscount(BigDecimal value) {
		return valuesWithNoDisc.put("unitValueFromGBDiscount", value);
	}

	public BigDecimal setUnitGrossValueFromGBDiscount(BigDecimal value) {
		return valuesWithNoDisc.put("unitGrossValueFromGBDiscount", value);
	}

	public BigDecimal getTotalValueWithoutDiscount() {
		return this.getUnitValueWithoutDiscount().multiply(new BigDecimal(super.getQuantity()));
	}

	public BigDecimal getTotalStValueWithoutDiscount() {
		return this.getUnitStValueWithoutDiscount().multiply(new BigDecimal(super.getQuantity()));
	}

	public BigDecimal getTotalGrossWithoutDiscount() {
		return this.getUnitGrossValueWithoutDiscount().multiply(new BigDecimal(super.getQuantity()));
	}

	public BigDecimal getTotalGrossAfterGlobalDiscount() {
		return this.getUnitGrossValueFromGBDiscount().multiply(new BigDecimal(super.getQuantity()));
	}
}
