package com.portal.client.dto;

public class ProductFileReadLayout {

	private String customerCode, customerStore;
	private byte[] xlsxStreams;

	public ProductFileReadLayout() {
		// TODO Auto-generated constructor stub
	}

	public byte[] getXlsxStreams() {
		return xlsxStreams;
	}

	public void setXlsxStreams(byte[] xlsxStreams) {
		this.xlsxStreams = xlsxStreams;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerStore() {
		return customerStore;
	}

	public void setCustomerStore(String customerStore) {
		this.customerStore = customerStore;
	}
}
