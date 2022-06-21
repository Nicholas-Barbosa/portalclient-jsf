package com.farawaybr.portal.dto;

public class MultipleProductRowExcelData extends ProductRowExcelData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1483840115845139306L;

	private int correctMultiple;

	public MultipleProductRowExcelData(ProductRowExcelData parent, int correctMultiple) {
		super(parent.getCode(), parent.getQuantity(), parent.getExcelRowIndex());
		this.correctMultiple = correctMultiple;
	}

	public int getCorrectMultiple() {
		return correctMultiple;
	}

}
