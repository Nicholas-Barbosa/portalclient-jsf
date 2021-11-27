package com.portal.client.service;

import com.portal.client.dto.IPGeoData;

public interface IpGeoService {

	IPGeoData findByIp(String ip);
}
