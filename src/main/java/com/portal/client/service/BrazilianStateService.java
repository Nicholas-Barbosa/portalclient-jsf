package com.portal.client.service;

import java.util.List;

import com.portal.client.vo.BrazilianState;

public interface BrazilianStateService {

	BrazilianState findByName(String name);

	BrazilianState findByAcronym(String name);

	List<BrazilianState> findAll();
}
