package com.portal.client.repository;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import com.portal.client.dto.BudgetFullProjection;
import com.portal.client.dto.ItemToFindPrice;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Page;

public interface BudgetRepository extends Serializable {

	Page<Budget> findAll(int page, int pageSize);

	Optional<BudgetFullProjection> findByCode(String code);

	void save(Budget request);

	void update(Budget budget);

	Budget estimate(String customerCode, String customerStore, Set<ItemToFindPrice> items)
			throws CustomerNotFoundException, ItemsNotFoundException;
}
