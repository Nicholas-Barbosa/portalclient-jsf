package com.farawaybr.portal.repository;

import com.farawaybr.portal.dto.UserData;

public interface RepresentativeRepository {

	/**
	 * Search for additional data and set it in the current api user object
	 * 
	 * @return
	 */
	UserData loadData();
}
