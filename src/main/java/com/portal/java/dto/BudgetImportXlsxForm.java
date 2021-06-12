package com.portal.java.dto;

public class BudgetImportXlsxForm {

	private int offsetRowForCustomerObject;
	private int offsetCellForCustomerCode;
	private int offSetCellForCustomerStore;
	private int offSetCellForCustomerState;
	private int offsetRowForItems;

	private byte[] xlsxStreams;

	public int getOffsetRowForCustomerObject() {
		return offsetRowForCustomerObject;
	}

	public void setOffsetRowForCustomerObject(int offsetRowForCustomerObject) {
		this.offsetRowForCustomerObject = offsetRowForCustomerObject;
	}

	public int getOffsetCellForCustomerCode() {
		return offsetCellForCustomerCode;
	}

	public void setOffsetCellForCustomerCode(int offsetCellForCustomerCode) {
		this.offsetCellForCustomerCode = offsetCellForCustomerCode;
	}

	public int getOffSetCellForCustomerStore() {
		return offSetCellForCustomerStore;
	}

	public void setOffSetCellForCustomerStore(int offSetCellForCustomerStore) {
		this.offSetCellForCustomerStore = offSetCellForCustomerStore;
	}

	public int getOffSetCellForCustomerState() {
		return offSetCellForCustomerState;
	}

	public void setOffSetCellForCustomerState(int offSetCellForCustomerState) {
		this.offSetCellForCustomerState = offSetCellForCustomerState;
	}

	public int getOffsetRowForItems() {
		return offsetRowForItems;
	}

	public void setOffsetRowForItems(int offsetRowForItems) {
		this.offsetRowForItems = offsetRowForItems;
	}

	public byte[] getXlsxStreams() {
		return xlsxStreams;
	}

	public void setXlsxStreams(byte[] xlsxStreams) {
		this.xlsxStreams = xlsxStreams;
	}
}
