package com.farawaybr.portal.osiip;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class IpInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String ip;
	private IpCountry country;
	private IpLocation location;

	@JsonbCreator
	public IpInfo(@JsonbProperty("ip") String ip, @JsonbProperty("country") IpCountry country,@JsonbProperty("location") IpLocation location) {
		super();
		this.ip = ip;
		this.country = country;
		this.location = location;
	}

	public String getIp() {
		return ip;
	}

	public IpCountry getCountry() {
		return country;
	}

	public IpLocation getLocation() {
		return location;
	}

}
