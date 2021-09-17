package com.portal.client.service;

import com.portal.client.security.user.User;

public interface RepresentativeService {

	/**
	 * Call RepresentativeRepository to search for additional data and set it in the
	 * current api user object
	 * 
	 * @return
	 */
	User getAdditionalData();
}
