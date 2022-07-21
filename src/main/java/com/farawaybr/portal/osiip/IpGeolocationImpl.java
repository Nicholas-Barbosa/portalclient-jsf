package com.farawaybr.portal.osiip;

import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.cdi.aop.annotations.NotFoundOptionalEmptyJoinPointCut;
import com.farawaybr.portal.jaxrs.client.RestClient;
import com.farawaybr.portal.resources.ConfigPropertiesResolver;

@ApplicationScoped
public class IpGeolocationImpl implements IpGeolocation {

	@Inject
	private ConfigPropertiesResolver configPropertiesResolver;

	@Inject
	private RestClient restClient;

	@NotFoundOptionalEmptyJoinPointCut
	@Override
	public Optional<IpInfo> findByIp(String ip) {
		// TODO Auto-generated method stub

		return Optional.of(restClient.get(configPropertiesResolver.getProperty("bigdata_ip_geolocation"), IpInfo.class,
				Map.of("ip", ip, "key", configPropertiesResolver.getProperty("bigdata_api_key")), null,
				"aplication/json", null));
	}

}
