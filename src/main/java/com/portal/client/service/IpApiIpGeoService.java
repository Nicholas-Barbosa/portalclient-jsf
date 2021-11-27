package com.portal.client.service;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.nicholas.jaxrsclient.RestClient;
import com.portal.client.dto.IPGeoData;

@ApplicationScoped
public class IpApiIpGeoService implements IpGeoService {

	@Inject
	private RestClient restClient;

	@Override
	public IPGeoData findByIp(String ip) {
		// TODO Auto-generated method stub
		return restClient.get("http://ip-api.com/json/{ip}", IPGeoData.class, null, Map.of("ip", ip),
				"application/json");
	}

}
