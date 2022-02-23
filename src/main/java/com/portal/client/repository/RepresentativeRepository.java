package com.portal.client.repository;

import com.portal.client.dto.RepresentativeData;

public interface RepresentativeRepository {

	/**
	 * Search for additional data and set it in the current api user object
	 * 
	 * @return
	 */
	RepresentativeData loadData();
}
