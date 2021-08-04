package com.portal.client.dto;

public class ItemXlsxFileLayout {

	private short offsetRowForItems;
	private short offSetCellForProductCode;
	private short offSetCellForProductQuantity;
	private short lastOffSetForItems;

	private byte[] xlsxStreams;

	public ItemXlsxFileLayout() {
		// TODO Auto-generated constructor stub
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
