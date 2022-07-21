package com.farawaybr.portal.osiip;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class IpLocation {

	private String continent, city, localityName, latitude, longituede;

	@JsonbCreator
	public IpLocation(@JsonbProperty("continent") String continent, @JsonbProperty("city") String city,
			@JsonbProperty("localityName") String localityName, @JsonbProperty("latitude") String latitude,
			@JsonbProperty("longitude") String longituede) {
		super();
		this.continent = continent;
		this.city = city;
		this.localityName = localityName;
		this.latitude = latitude;
		this.longituede = longituede;
	}

	public String getContinent() {
		return continent;
	}

	public String getCity() {
		return city;
	}

	public String getLocalityName() {
		return localityName;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongituede() {
		return longituede;
	}

}
