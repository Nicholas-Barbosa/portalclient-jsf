package com.farawaybr.portal.service;

import com.farawaybr.portal.dto.IPGeoData;

public interface IpGeoService {

	IPGeoData findByIp(String ip);
}
