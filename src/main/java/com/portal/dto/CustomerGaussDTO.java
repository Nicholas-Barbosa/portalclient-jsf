package com.portal.dto;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;

import com.portal.pojo.Customer;

public class CustomerGaussDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5152498286178192330L;
	@JsonbProperty
	private String address;
	@JsonbProperty
	private String code;
	@JsonbProperty
	private String store;
	@JsonbProperty
	private String state;
	@JsonbProperty
	private String cgc;
	@JsonbProperty
	private String blocked;
	@JsonbProperty
	private String name;

	@JsonbProperty("fantasy_name")
	private String fantasyName;
	@JsonbProperty
	private String city;
	@JsonbProperty
	private String table;

	public CustomerGaussDTO() {
		// TODO Auto-generated constructor stub
	}

	public CustomerGaussDTO(String address, String code, String store, String state, String cgc, String blocked,
			String name, String fantasyName, String city, String table) {
		super();
		this.address = address;
		this.code = code;
		this.store = store;
		this.state = state;
		this.cgc = cgc;
		this.blocked = blocked;
		this.name = name;
		this.fantasyName = fantasyName;
		this.city = city;
	}

	public Customer toCustomer() {
		return new Customer(address, code, store, state, cgc, blocked, name, fantasyName, city, table);
	}

	public String getAddress() {
		return address;
	}

	public String getCode() {
		return code;
	}

	public String getStore() {
		return store;
	}

	public String getState() {
		return state;
	}

	public String getCgc() {
		return cgc;
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

	public String getCity() {
		return city;
	}

	public String getTable() {
		return table;
	}

	@Override
	public String toString() {
		return "GssCustomerDTO [address=" + address + ", code=" + code + ", store=" + store + ", state=" + state
				+ ", cgc=" + cgc + ", blocked=" + blocked + ", name=" + name + ", fantasyName=" + fantasyName
				+ ", city=" + city + ", table=" + table + "]";
	}

}
