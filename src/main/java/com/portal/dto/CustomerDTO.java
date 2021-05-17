package com.portal.dto;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class CustomerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5152498286178192330L;
	private String address;

	private String code;
	private String store;
	private String state;
	private String cgc;
	private String blocked;
	private String name;

	private String fantasyName;
	private String city;
	private String table;

	public CustomerDTO() {
		// TODO Auto-generated constructor stub
	}

	@JsonbCreator
	public CustomerDTO(@JsonbProperty("address") String address, @JsonbProperty("code") String code,
			@JsonbProperty("store") String store, @JsonbProperty("state") String state,
			@JsonbProperty("cgc") String cgc, @JsonbProperty("blocked") String blocked,
			@JsonbProperty("name") String name, @JsonbProperty("fantasy_name") String fantasyName,
			@JsonbProperty("city") String city, @JsonbProperty("table") String table) {
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

	

	public CustomerDTO(CustomerDTO customerDTO) {
		this(customerDTO.address, customerDTO.code, customerDTO.store, customerDTO.state, customerDTO.cgc,
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
		return "CustomerDTO [address=" + address + ", code=" + code + ", store=" + store + ", state=" + state + ", cgc="
				+ cgc + ", blocked=" + blocked + ", name=" + name + ", fantasyName=" + fantasyName + ", city=" + city
				+ ", table=" + table + "]";
	}

}
