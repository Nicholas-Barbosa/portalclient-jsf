package com.portal.java.dto;

public class BudgetImportXlsxForm {

	private short offsetRowForCustomerObject;
	private short offsetCellForCustomerCode;
	private short offSetCellForCustomerStore;
	private short offSetCellForCustomerState;

	private short offsetRowForItems;
	private short offSetCellForProductCode;
	private short offSetCellForProductQuantity;
	private short lastOffSetForItems;

	private byte[] xlsxStreams;

	public short getOffsetRowForCustomerObject() {
		return offsetRowForCustomerObject;
	}

	public void setOffsetRowForCustomerObject(short offsetRowForCustomerObject) {
		this.offsetRowForCustomerObject = offsetRowForCustomerObject;
	}

	public short getOffsetCellForCustomerCode() {
		return offsetCellForCustomerCode;
	}

	public void setOffsetCellForCustomerCode(short offsetCellForCustomerCode) {
		this.offsetCellForCustomerCode = offsetCellForCustomerCode;
	}

	public short getOffSetCellForCustomerStore() {
		return offSetCellForCustomerStore;
	}

	public void setOffSetCellForCustomerStore(short offSetCellForCustomerStore) {
		this.offSetCellForCustomerStore = offSetCellForCustomerStore;
	}

	public short getOffSetCellForCustomerState() {
		return offSetCellForCustomerState;
	}

	public void setOffSetCellForCustomerState(short offSetCellForCustomerState) {
		this.offSetCellForCustomerState = offSetCellForCustomerState;
	}

	public short getOffsetRowForItems() {
		return offsetRowForItems;
	}

	public void setOffsetRowForItems(short offsetRowForItems) {
		this.offsetRowForItems = offsetRowForItems;
	}

	public short getOffSetCellForProductCode() {
		return offSetCellForProductCode;
	}

	public void setOffSetCellForProductCode(short offSetCellForProductCode) {
		this.offSetCellForProductCode = offSetCellForProductCode;
	}

	public short getOffSetCellForProductQuantity() {
		return offSetCellForProductQuantity;
	}

	public void setOffSetCellForProductQuantity(short offSetCellForProductQuantity) {
		this.offSetCellForProductQuantity = offSetCellForProductQuantity;
	}

	public short getLastOffSetForItems() {
		return lastOffSetForItems;
	}

	public void setLastOffSetForItems(short lastOffSetForItems) {
		this.lastOffSetForItems = lastOffSetForItems;
	}

	public byte[] getXlsxStreams() {
		return xlsxStreams;
	}

	public void setXlsxStreams(byte[] xlsxStreams) {
		this.xlsxStreams = xlsxStreams;
	}

}
