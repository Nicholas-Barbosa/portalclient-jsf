package com.farawaybr.portal.repository;

import java.io.Serializable;
import java.util.Optional;

import com.farawaybr.portal.dto.BudgetFullProjection;
import com.farawaybr.portal.dto.BudgetSemiProjection;
import com.farawaybr.portal.vo.Budget;
import com.farawaybr.portal.vo.Page;

public interface BudgetRepository extends Serializable {

	Optional<Page<BudgetSemiProjection>> findAll(int page, int pageSize, String key);

	Optional<BudgetFullProjection> findByCode(String code);

	void save(Budget request);

	void update(Budget budget);

}
