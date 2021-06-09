package com.portal.java.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.java.pojo.ObjectValueHolder;

public class EstimatedItemDTO extends BaseProductDTO {

	private BigDecimal unitGrossValue;

	private BigDecimal totalGrossValue;

	private BigDecimal unitPrice;

	private BigDecimal totale;

	private BigDecimal stValue;

	private BigDecimal unitStValue;

	private Integer avaliableStock;

	private final Map<String, ObjectValueHolder> objectValueHolder;

	@JsonbCreator
	public EstimatedItemDTO(@JsonbProperty("unit_gross_value") BigDecimal unitGrossValue,
			@JsonbProperty("total_gross_value") BigDecimal totalGrossValue, @JsonbProperty("product_code") String code,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("unit_price") BigDecimal unitPrice,
			@JsonbProperty("quantity") int quantity, @JsonbProperty("total_price") BigDecimal totale,
			@JsonbProperty("st_value") BigDecimal stValue, @JsonbProperty("line_discount") BigDecimal discount,
			@JsonbProperty("available_stock") Integer avaliableStock) {
		super(code, commercialCode, quantity, new BigDecimal(0));
		this.avaliableStock = avaliableStock;
		this.unitGrossValue = unitGrossValue;
		this.totalGrossValue = totalGrossValue;
		this.unitPrice = unitPrice;
		this.totale = totale;
		this.stValue = stValue;
		this.avaliableStock = avaliableStock;
		this.unitStValue = stValue.divide(new BigDecimal(quantity), 2, RoundingMode.HALF_UP);
		this.objectValueHolder = new HashMap<>();
		putInitialValuesOnFieldsValueHolder();
	}

	public void setBaseProductAttr(BaseProductDTO base) {
		super.setDescription(base.getDescription());
		super.setMultiple(base.getMultiple());
		super.setInfo(base.getInfo());
	}

	public void setDescription(String description) {
		super.setDescription(description);
	}

	public BigDecimal getUnitGrossValue() {
		return unitGrossValue;
	}

	public BigDecimal getUnitGrossValueWithNoDiscount() {
		return (BigDecimal) objectValueHolder.get("unitGrossValue").getCurrentValue();
	}

	public void setUnitGrossValue(BigDecimal unitGrossValue) {
		this.unitGrossValue = unitGrossValue;
	}

	public BigDecimal getTotalGrossValue() {
		return totalGrossValue;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public BigDecimal getUnitValueWithNoDiscount() {
		return (BigDecimal) objectValueHolder.get("unitPrice").getCurrentValue();
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotale() {
		return totale;
	}

	public BigDecimal getStValue() {
		return stValue;
	}

	public void setStValue(BigDecimal stValue) {
		this.stValue = stValue;
	}

	public BigDecimal getUnitStValue() {
		return unitStValue;
	}

	public BigDecimal getUnitStValueWithNoDiscount() {
		return (BigDecimal) objectValueHolder.get("unitStValue").getCurrentValue();
	}

	public void setUnitStValue(BigDecimal unitStValue) {
		this.unitStValue = unitStValue;
	}

	public void setDiscount(BigDecimal discount) {
		setNewValueToObjectValueHolder("discount", discount);
		super.setDiscount(discount);
	}

	public void setQuantity(int quantity) {
		setNewValueToObjectValueHolder("quantity", quantity);
		super.setQuantity(quantity);
	}

	public Integer getAvaliableStock() {
		return avaliableStock;
	}

	public final void setTotalGrossValue(BigDecimal newTotal) {
		this.totalGrossValue = newTotal;
	}

	public final void setTotalPrice(BigDecimal newTotal) {
		this.totale = newTotal;
	}

	public final void changeTotalStValue(BigDecimal newTotal) {
		this.stValue = newTotal;
	}

	public Map<String, ObjectValueHolder> getFieldsValueHolder() {
		return Collections.unmodifiableMap(objectValueHolder);
	}

	public boolean checkCurrentAndOldQuantity() {
		ObjectValueHolder values = objectValueHolder.get("quantity");
		int currentQuantity = (Integer) values.getCurrentValue();
		int oldQuantity = (Integer) values.getOldValue();
		return currentQuantity == oldQuantity;
	}

	public boolean checkCurrentAndOldDiscount() {
		ObjectValueHolder values = objectValueHolder.get("discount");
		BigDecimal currentQuantity = (BigDecimal) values.getCurrentValue();
		BigDecimal oldQuantity = (BigDecimal) values.getOldValue();
		return currentQuantity.equals(oldQuantity);
	}

	public BigDecimal getOriginalStValue() {
		return (BigDecimal) objectValueHolder.get("stValue").getCurrentValue();
	}

	private void setNewValueToObjectValueHolder(String objectKey, Object newValue) {
		ObjectValueHolder object = objectValueHolder.get(objectKey);
		if (object != null) {
			Object oldValue = object.getCurrentValue();
			object.setOldValue(oldValue).setCurrentValue(newValue);
		}
	}

	private void putInitialValuesOnFieldsValueHolder() {
		objectValueHolder.put("unitGrossValue", new ObjectValueHolder(unitGrossValue, unitGrossValue));
		objectValueHolder.put("totalGrossValue", new ObjectValueHolder(totalGrossValue, totalGrossValue));
		objectValueHolder.put("unitPrice", new ObjectValueHolder(unitPrice, unitPrice));
		objectValueHolder.put("totale", new ObjectValueHolder(totale, totale));
		objectValueHolder.put("stValue", new ObjectValueHolder(stValue, stValue));
		objectValueHolder.put("discount", new ObjectValueHolder(super.getDiscount(), super.getDiscount()));
		objectValueHolder.put("quantity", new ObjectValueHolder(super.getQuantity(), super.getQuantity()));
		objectValueHolder.put("unitStValue", new ObjectValueHolder(unitStValue, unitStValue));
	}

}
