package com.portal.client.service;

import com.portal.client.security.user.RepresentativeUser;

public interface RepresentativeService {

	/**
	 * Call RepresentativeRepository to make a request to /representative endpint
	 * 
	 * @return
	 */
	RepresentativeUser find();
}
