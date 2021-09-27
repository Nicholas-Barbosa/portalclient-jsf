package com.portal.client.repository;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import com.portal.client.dto.ItemToFindPrice;
import com.portal.client.dto.Page;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;
import com.portal.client.vo.Budget;

public interface BudgetRepository extends Serializable {

	Page<Budget> findAll(int page, int pageSize);

	Optional<Page<Budget>> findByCode(String code, int page, int pageSize);

	void save(Budget request);

	void update(Budget budget);

	Budget estimate(String customerCode, String customerStore, Set<ItemToFindPrice> items)
			throws CustomerNotFoundException, ItemsNotFoundException;
}
