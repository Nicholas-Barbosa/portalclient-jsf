package com.portal.client.dto;

public class ItemXlsxFileLayout {

	private int initPosition;
	private int offSetCellForProductCode;
	private int offSetCellForProductQuantity;
	private int lastPosition;

	private byte[] xlsxStreams;

	public ItemXlsxFileLayout() {
		// TODO Auto-generated constructor stub
	}

	public int getInitPosition() {
		return initPosition;
	}

	public void setInitPosition(int offsetRowForItems) {
		this.initPosition = offsetRowForItems;
	}

	public int getOffSetCellForProductCode() {
		return offSetCellForProductCode;
	}

	public void setOffSetCellForProductCode(int offSetCellForProductCode) {
		this.offSetCellForProductCode = offSetCellForProductCode;
	}

	public int getOffSetCellForProductQuantity() {
		return offSetCellForProductQuantity;
	}

	public void setOffSetCellForProductQuantity(int offSetCellForProductQuantity) {
		this.offSetCellForProductQuantity = offSetCellForProductQuantity;
	}

	public int getLastPosition() {
		return lastPosition;
	}

	public void setLastPositions(int lastOffSetForItems) {
		this.lastPosition = lastOffSetForItems;
	}

	public byte[] getXlsxStreams() {
		return xlsxStreams;
	}

	public void setXlsxStreams(byte[] xlsxStreams) {
		this.xlsxStreams = xlsxStreams;
	}

}
