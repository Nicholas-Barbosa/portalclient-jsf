package com.portal.java.service;

import java.util.List;

import com.portal.java.dto.BrazilianState;

public interface BrazilianStateService {

	BrazilianState findByName(String name);

	BrazilianState findByAcronym(String name);

	List<BrazilianState> findAll();
}
