package com.portal.client.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.CustomerProductPriceTable;

public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5152498286178192330L;
	private final String code;

	private final String store;
	private final String cnpj;
	private final String blocked;
	private final String name;
	private final String fantasyName;
	private final CustomerAddress address;
	private final CustomerPurchaseInfo financialInfo;
	private final CustomerContact contact;
	private CustomerProductPriceTable priceTable;

	@JsonbCreator
	public static Customer ofJsonb(@JsonbProperty("code") String code, @JsonbProperty("store") String store,
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
		return new Customer(code, store, cnpj, blocked, name, fantasyName, address, purchaseInfo, contact);

	}

	private static LocalDateTime formatLastPurschase(String text) {
		try {
			return LocalDateTime.parse(text.substring(0, text.lastIndexOf("-")),
					DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
		} catch (Exception e) {
			return LocalDateTime.of(2004, 6, 14, 20, 10, 10);
		}
	}

	public Customer(String code, String store, String cnpj, String blocked, String name, String fantasyName,
			CustomerAddress address, CustomerPurchaseInfo financialInfo, CustomerContact contact) {
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
	}

	public Customer(Customer customer) {
		this(customer.code, customer.store, customer.cnpj, customer.blocked, customer.name, customer.fantasyName,
				customer.address, customer.financialInfo, customer.contact);
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
		return priceTable == null ? financialInfo.getTable() : priceTable.getCode();
	}

}
