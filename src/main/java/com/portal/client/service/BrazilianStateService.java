package com.portal.client.service;

import java.util.List;

import com.portal.client.dto.BrazilianState;

public interface BrazilianStateService {

	BrazilianState findByName(String name);

	BrazilianState findByAcronym(String name);

	List<BrazilianState> findAll();
}
