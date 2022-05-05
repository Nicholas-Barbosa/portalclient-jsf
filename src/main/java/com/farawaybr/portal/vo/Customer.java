package com.farawaybr.portal.vo;

import java.io.Serializable;

public class Customer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1123063573059473915L;

	private String code;

	private String store;
	private String cnpj;
	private String blocked;
	private String name;
	private String fantasyName;
	private CustomerAddress address;
	private CustomerPurchaseInfo financialInfo;
	private CustomerContact contact;
	private CustomerProductPriceTable priceTable;

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public Customer(String code, String store, String cnpj, String blocked, String name, String fantasyName,
			CustomerAddress address, CustomerPurchaseInfo financialInfo, CustomerContact contact,
			CustomerProductPriceTable priceTable) {
		super();
		this.code = code;
		this.store = store;
		this.cnpj = cnpj;
		this.blocked = blocked;
		this.name = name;
		this.fantasyName = fantasyName;
		this.address = address;
		this.financialInfo = financialInfo;
		this.contact = contact;
		this.priceTable = priceTable;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getBlocked() {
		return blocked;
	}

	public void setBlocked(String blocked) {
		this.blocked = blocked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFantasyName() {
		return fantasyName;
	}

	public void setFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
	}

	public CustomerAddress getAddress() {
		return address;
	}

	public void setAddress(CustomerAddress address) {
		this.address = address;
	}

	public CustomerPurchaseInfo getFinancialInfo() {
		return financialInfo;
	}

	public void setFinancialInfo(CustomerPurchaseInfo financialInfo) {
		this.financialInfo = financialInfo;
	}

	public CustomerContact getContact() {
		return contact;
	}

	public void setContact(CustomerContact contact) {
		this.contact = contact;
	}

	public CustomerProductPriceTable getPriceTable() {
		return priceTable;
	}

	public void setPriceTable(CustomerProductPriceTable priceTable) {
		this.priceTable = priceTable;
	}

	public String getPriceTableCode() {
		return priceTable == null ? financialInfo != null ? financialInfo.getTable() : null : priceTable.getCode();
	}
}
