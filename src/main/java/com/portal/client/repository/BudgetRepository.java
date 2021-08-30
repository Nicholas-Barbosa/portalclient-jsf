package com.portal.client.repository;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.ItemToFindPrice;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;
import com.portal.client.vo.Budget;

public interface BudgetRepository extends Serializable {

	BudgetPage findAll(int page, int pageSize);

	Optional<Budget> findByCode(String code);

	void save(Budget request);

	Budget estimate(String customerCode, String customerStore, Set<ItemToFindPrice> items)
			throws CustomerNotFoundException, ItemsNotFoundException;
}
