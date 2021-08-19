package com.portal.client.repository;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.ItemToFindPrice;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;

public interface BudgetRepository extends Serializable {

	BudgetPage findAll(int page, int pageSize);

	Optional<BaseBudget> findByCode(String code);

	void save(BaseBudget request);

	BaseBudget estimate(String customerCode, String customerStore, Set<ItemToFindPrice> items)
			throws CustomerNotFoundException, ItemsNotFoundException;
}
