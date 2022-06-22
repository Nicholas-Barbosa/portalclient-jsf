package com.farawaybr.portal.service;

import com.farawaybr.portal.security.user.ProtheusUser;

public interface RepresentativeService {

	/**
	 * Call RepresentativeRepository to make a request to /representative endpint
	 * 
	 * @return
	 */
	ProtheusUser find();
}
