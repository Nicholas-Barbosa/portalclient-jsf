package com.portal.client.repository;

import java.io.Serializable;
import java.util.Optional;

import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.dto.BudgetSemiProjection;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Page;

public interface BudgetRepository extends Serializable {

	Optional<Page<BudgetSemiProjection>> findAll(int page, int pageSize, String key);

	Optional<BudgetFullProjection> findByCode(String code);

	void save(Budget request);

	void update(Budget budget);

}
