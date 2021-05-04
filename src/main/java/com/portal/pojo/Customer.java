package com.portal.pojo;

import java.io.Serializable;

public class Customer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9088263539208735631L;
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

	public Customer(String address, String code, String store, String state, String cgc, String blocked, String name,
			String fantasyName, String city, String table) {
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
		this.table = table;
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

}
