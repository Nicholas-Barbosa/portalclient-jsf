package com.farawaybr.portal.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

import com.farawaybr.portal.vo.Customer;
import com.farawaybr.portal.vo.CustomerAddress;
import com.farawaybr.portal.vo.CustomerContact;
import com.farawaybr.portal.vo.CustomerProductPriceTable;
import com.farawaybr.portal.vo.CustomerPurchaseInfo;

public class CustomerJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5152498286178192330L;
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
	@JsonbTransient
	private Customer customer;

	public CustomerJson() {
		// TODO Auto-generated constructor stub
	}

	@JsonbCreator
	public CustomerJson(@JsonbProperty("code") String code, @JsonbProperty("store") String store,
			@JsonbProperty("cgc") String cnpj, @JsonbProperty("blocked") String blocked,
			@JsonbProperty("name") String name, @JsonbProperty("fantasy_name") String fantasyName,
			@JsonbProperty("address") String street, @JsonbProperty("limit") double limit,
			@JsonbProperty("district") String district, @JsonbProperty("last_purchase") String lastPurchase,
			@JsonbProperty("risk") String risk, @JsonbProperty("discount") float discount,
			@JsonbProperty("discount2") float discount2, @JsonbProperty("discount3") float discount3,
			@JsonbProperty("state") String state, @JsonbProperty("table") String table,
			@JsonbProperty("email") String email, @JsonbProperty("email2") String email2,
			@JsonbProperty("payment_terms") String paymentTerms, @JsonbProperty("city") String city,
			@JsonbProperty("zip_code") String zipCode) {
		CustomerAddress address = new CustomerAddress(street, district, city, zipCode, state);
		CustomerContact contact = new CustomerContact(email, email2);

		CustomerPurchaseInfo purchaseInfo = new CustomerPurchaseInfo(discount, discount2, discount3,
				formatLastPurschase(lastPurchase), risk.length() > 0 ? risk.charAt(0) : '-', paymentTerms, table,
				limit);
		this.code = code;
		this.store = store;
		this.cnpj = cnpj;
		this.blocked = blocked;
		this.name = name;
		this.fantasyName = fantasyName;
		this.address = address;
		this.financialInfo = purchaseInfo;
		this.contact = contact;
		customer = new Customer(code, store, cnpj, blocked, name, fantasyName, address, purchaseInfo, contact,
				priceTable);
	}

	private static LocalDateTime formatLastPurschase(String text) {
		try {
			return LocalDateTime.parse(text.substring(0, text.lastIndexOf("-")),
					DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
		} catch (Exception e) {
			return LocalDateTime.of(2004, 6, 14, 20, 10, 10);
		}
	}

	public String getCode() {
		return code;
	}

	public String getStore() {
		return store;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getBlocked() {
		return blocked;
	}

	public String getName() {
		return name;
	}

	public String getFantasyName() {
		return fantasyName;
	}

	public CustomerAddress getAddress() {
		return address;
	}

	public CustomerPurchaseInfo getFinancialInfo() {
		return financialInfo;
	}

	public CustomerContact getContact() {
		return contact;
	}

	public String getStreet() {
		return this.address.getStreet();
	}

	public String getDistrict() {
		return address.getDistrict();
	}

	public String getZipCode() {
		return address.getZipCode();
	}

	public String getCity() {
		return address.getCity();
	}

	public String getState() {
		return address.getState();
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

	public Customer getCustomer() {
		return this.customer;
	}
}
