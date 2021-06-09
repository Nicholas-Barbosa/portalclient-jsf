package com.portal.java.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.java.pojo.ObjectValue;

public class EstimatedItemDTO extends BaseProductDTO {

	private BigDecimal unitGrossValue;

	private BigDecimal totalGrossValue;

	private BigDecimal unitPrice;

	private BigDecimal totale;

	private BigDecimal stValue;

	private BigDecimal unitStValue;

	private BigDecimal discount;

	private int quantity;

	private Integer avaliableStock;

	private final Map<String, ObjectValue> fieldsValueHolder;

	@JsonbCreator
	public EstimatedItemDTO(@JsonbProperty("unit_gross_value") BigDecimal unitGrossValue,
			@JsonbProperty("total_gross_value") BigDecimal totalGrossValue, @JsonbProperty("product_code") String code,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("unit_price") BigDecimal unitPrice,
			@JsonbProperty("quantity") int quantity, @JsonbProperty("total_price") BigDecimal totale,
			@JsonbProperty("st_value") BigDecimal stValue, @JsonbProperty("line_discount") BigDecimal discount,
			@JsonbProperty("available_stock") Integer avaliableStock) {
		super(code, null, commercialCode, null, null, null);
		this.avaliableStock = avaliableStock;
		this.unitGrossValue = unitGrossValue;
		this.totalGrossValue = totalGrossValue;
		this.unitPrice = unitPrice;
		this.totale = totale;
		this.stValue = stValue;
		this.discount = discount;
		this.quantity = quantity;
		this.avaliableStock = avaliableStock;
		this.unitStValue = stValue.divide(new BigDecimal(quantity), 2, RoundingMode.HALF_UP);
		this.fieldsValueHolder = new HashMap<>();
		fieldsValueHolder.put("quantity", new ObjectValue(quantity, quantity));
		fieldsValueHolder.put("discount", new ObjectValue(discount, discount));
	}

	public EstimatedItemDTO(BigDecimal unitGrossValue, BigDecimal totalGrossValue, BigDecimal unitPrice,
			BigDecimal totale, BigDecimal stValue, BigDecimal unitStValue, BigDecimal discount, int quantity,
			Integer avaliableStock) {
		super();
		this.unitGrossValue = unitGrossValue;
		this.totalGrossValue = totalGrossValue;
		this.unitPrice = unitPrice;
		this.totale = totale;
		this.stValue = stValue;
		this.unitStValue = unitStValue;
		this.discount = discount;
		this.quantity = quantity;
		this.avaliableStock = avaliableStock;
		this.fieldsValueHolder = new HashMap<>();
		fieldsValueHolder.put("quantity", new ObjectValue(quantity, quantity));
		fieldsValueHolder.put("discount", new ObjectValue(discount, discount));
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

	public BigDecimal getTotalGrossValue() {
		return totalGrossValue;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public BigDecimal getTotale() {
		return totale;
	}

	public BigDecimal getStValue() {
		return stValue;
	}

	public BigDecimal getUnitStValue() {
		return unitStValue;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		ObjectValue objectValue = this.fieldsValueHolder.get("discount");
		objectValue.setCurrentValue(discount).setOldValue(this.discount);
		this.discount = discount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		ObjectValue objectValue = this.fieldsValueHolder.get("quantity");
		objectValue.setCurrentValue(quantity).setOldValue(this.quantity);
		this.quantity = quantity;
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

	public final void setTotalStValue(BigDecimal newTotal) {
		this.stValue = newTotal;
	}

	public Map<String, ObjectValue> getFieldsValueHolder() {
		return Collections.unmodifiableMap(fieldsValueHolder);
	}

	public boolean checkCurrentAndOldQuantity() {
		ObjectValue values = fieldsValueHolder.get("quantity");
		int currentQuantity = (Integer) values.getCurrentValue();
		int oldQuantity = (Integer) values.getOldValue();
		return currentQuantity == oldQuantity;
	}

	public boolean checkCurrentAndOldDiscount() {
		ObjectValue values = fieldsValueHolder.get("discount");
		BigDecimal currentQuantity = (BigDecimal) values.getCurrentValue();
		BigDecimal oldQuantity = (BigDecimal) values.getOldValue();
		return currentQuantity.equals(oldQuantity);
	}
}
