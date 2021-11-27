package com.portal.client.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class IPGeoData {

	private final String ip, country, countryCode, region, regionName, city;
	private final float lat, lon;

	@JsonbCreator
	public IPGeoData(@JsonbProperty("query") String ip, @JsonbProperty("country") String country,
			@JsonbProperty("countryCode") String countryCode, @JsonbProperty("region") String region,
			@JsonbProperty("regionName") String regionName, @JsonbProperty("city") String city,
			@JsonbProperty("lat") float lat, @JsonbProperty("lon") float lon) {
		super();
		this.ip = ip;
		this.country = country;
		this.countryCode = countryCode;
		this.region = region;
		this.regionName = regionName;
		this.city = city;
		this.lat = lat;
		this.lon = lon;
	}

	public String getIp() {
		return ip;
	}

	public String getCountry() {
		return country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getRegion() {
		return region;
	}

	public String getRegionName() {
		return regionName;
	}

	public String getCity() {
		return city;
	}

	public float getLat() {
		return lat;
	}

	public float getLon() {
		return lon;
	}

	@Override
	public String toString() {
		return "IPGeoData [ip=" + ip + ", country=" + country + ", countryCode=" + countryCode + ", region=" + region
				+ ", regionName=" + regionName + ", city=" + city + ", lat=" + lat + ", lon=" + lon + "]";
	}

}
