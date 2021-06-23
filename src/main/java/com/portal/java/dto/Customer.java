package com.portal.java.dto;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5152498286178192330L;
	private final String address;
	private final String code;
	private final String store;
	private final String state;
	private final String cnpj;
	private final String blocked;
	private final String name;
	private final String fantasyName;
	private final String city;
	private final String table;

	@JsonbCreator
	public Customer(@JsonbProperty("address") String address, @JsonbProperty("code") String code,
			@JsonbProperty("store") String store, @JsonbProperty("state") String state,
			@JsonbProperty("cgc") String cgc, @JsonbProperty("blocked") String blocked,
			@JsonbProperty("name") String name, @JsonbProperty("fantasy_name") String fantasyName,
			@JsonbProperty("city") String city, @JsonbProperty("table") String table) {
		super();
		this.address = address;
		this.code = code;
		this.store = store;
		this.state = state;
		this.cnpj = cgc;
		this.blocked = blocked;
		this.name = name;
		this.fantasyName = fantasyName;
		this.city = city;
		this.table = table;
	}

	public Customer(Customer customerDTO) {
		this(customerDTO.address, customerDTO.code, customerDTO.store, customerDTO.state, customerDTO.cnpj,
				customerDTO.blocked, customerDTO.name, customerDTO.fantasyName, customerDTO.city, customerDTO.table);
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

	public String getCity() {
		return city;
	}

	public String getTable() {
		return table;
	}

	@Override
	public String toString() {
		return "CustomerDTO [address=" + address + ", code=" + code + ", store=" + store + ", state=" + state + ", cgc="
				+ cnpj + ", blocked=" + blocked + ", name=" + name + ", fantasyName=" + fantasyName + ", city=" + city
				+ ", table=" + table + "]";
	}

}
