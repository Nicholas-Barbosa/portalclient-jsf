package com.portal.java.pojo;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class Cep {

	private final String cep;
	private final String address;
	private final String city;
	private final String state;
	private final String district;
	private final String lat;
	private final String lng;

	@JsonbCreator
	public Cep(@JsonbProperty("cep") String cep, @JsonbProperty("address") String address,
			@JsonbProperty("city") String city, @JsonbProperty("state") String state, @JsonbProperty("lat") String lat,
			@JsonbProperty("lng") String lng, @JsonbProperty("district") final String district) {
		super();
		this.cep = cep;
		this.address = address;
		this.city = city;
		this.state = state;
		this.lat = lat;
		this.lng = lng;
		this.district = district;
	}

	public String getCep() {
		return cep;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getLat() {
		return lat;
	}

	public String getLng() {
		return lng;
	}

	public String getDistrict() {
		return district;
	}
}
