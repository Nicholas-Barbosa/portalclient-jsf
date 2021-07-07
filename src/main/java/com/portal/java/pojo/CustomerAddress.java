package com.portal.java.pojo;

public final class CustomerAddress {

	private final String street;
	private final String district;
	private final String city;
	private final String zipCode;
	private final String state;

	public CustomerAddress(String street, String district, String city, String zipCode, String state) {
		super();
		this.street = street;
		this.district = district;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public String getDistrict() {
		return district;
	}

	public String getCity() {
		return city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getState() {
		return state;
	}

}
