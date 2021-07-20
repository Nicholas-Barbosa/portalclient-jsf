package com.portal.client.dto;

import java.util.Arrays;

public class BudgetXlsxPreviewForm {

	private short offsetRowForCustomerObject;
	private short offsetCellForCustomerName;
	private short offSetCellForCustomerStore;
	private short offSetCellForCustomerState;

	private short offsetRowForItems;
	private short offSetCellForProductCode;
	private short offSetCellForProductQuantity;
	private short lastOffSetForItems;

	private byte[] xlsxStreams;

	public BudgetXlsxPreviewForm() {
		// TODO Auto-generated constructor stub
	}

	public BudgetXlsxPreviewForm(short offsetRowForCustomerObject, short offsetCellForCustomerName,
			short offSetCellForCustomerStore, short offSetCellForCustomerState, short offsetRowForItems,
			short offSetCellForProductCode, short offSetCellForProductQuantity, short lastOffSetForItems
			) {
		super();
		this.offsetRowForCustomerObject = offsetRowForCustomerObject;
		this.offsetCellForCustomerName = offsetCellForCustomerName;
		this.offSetCellForCustomerStore = offSetCellForCustomerStore;
		this.offSetCellForCustomerState = offSetCellForCustomerState;
		this.offsetRowForItems = offsetRowForItems;
		this.offSetCellForProductCode = offSetCellForProductCode;
		this.offSetCellForProductQuantity = offSetCellForProductQuantity;
		this.lastOffSetForItems = lastOffSetForItems;
	}

	public short getOffsetRowForCustomerObject() {
		return offsetRowForCustomerObject;
	}

	public void setOffsetRowForCustomerObject(short offsetRowForCustomerObject) {
		this.offsetRowForCustomerObject = offsetRowForCustomerObject;
	}

	public short getOffsetCellForCustomerName() {
		return offsetCellForCustomerName;
	}
	public void setOffsetCellForCustomerName(short offsetCellForCustomerName) {
		this.offsetCellForCustomerName = offsetCellForCustomerName;
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

	@Override
	public String toString() {
		return "BudgetImportXlsxForm [offsetRowForCustomerObject=" + offsetRowForCustomerObject
				+ ", offsetCellForCustomerName=" + offsetCellForCustomerName + ", offSetCellForCustomerStore="
				+ offSetCellForCustomerStore + ", offSetCellForCustomerState=" + offSetCellForCustomerState
				+ ", offsetRowForItems=" + offsetRowForItems + ", offSetCellForProductCode=" + offSetCellForProductCode
				+ ", offSetCellForProductQuantity=" + offSetCellForProductQuantity + ", lastOffSetForItems="
				+ lastOffSetForItems + ", xlsxStreams=" + Arrays.toString(xlsxStreams) + "]";
	}

}
