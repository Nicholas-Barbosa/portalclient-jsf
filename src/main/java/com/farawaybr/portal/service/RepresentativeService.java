package com.farawaybr.portal.service;

import com.farawaybr.portal.security.user.RepresentativeUser;

public interface RepresentativeService {

	/**
	 * Call RepresentativeRepository to make a request to /representative endpint
	 * 
	 * @return
	 */
	RepresentativeUser find();
}
