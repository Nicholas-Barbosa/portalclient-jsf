package com.portal.client.pojo;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ZipCode {

	private final String cep;
	private final String street;
	private final String city;
	private final String state;
	private final String district;
	private final Double lat;
	private final Double lng;

	@JsonbCreator
	public ZipCode(@JsonbProperty("cep") String cep, @JsonbProperty("address") String street,
			@JsonbProperty("city") String city, @JsonbProperty("state") String state, @JsonbProperty("lat") Double lat,
			@JsonbProperty("lng") Double lng, @JsonbProperty("district") final String district) {
		super();
		this.cep = cep;
		this.street = street;
		this.city = city;
		this.state = state;
		this.lat = lat;
		this.lng = lng;
		this.district = district;
	}

	public String getCep() {
		return cep;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLng() {
		return lng;
	}

	public String getDistrict() {
		return district;
	}
}
