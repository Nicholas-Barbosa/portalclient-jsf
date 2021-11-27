package com.portal.client.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.nicholas.jaxrsclient.RestClient;
import com.portal.client.dto.IPGeoData;

@ApplicationScoped
public class IpApiIpGeoService implements IpGeoService{

	@Inject
	private RestClient restClient;
	
	@Override
	public IPGeoData findByIp(String ip) {
		// TODO Auto-generated method stub
		return restClient.get("ip-api.com/json", null, null, null, "application/json");
	}

}
