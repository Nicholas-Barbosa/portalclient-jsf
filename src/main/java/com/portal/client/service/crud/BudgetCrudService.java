package com.portal.client.service.crud;

import java.util.Optional;
import java.util.Set;

import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.dto.ItemToFindPrice;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;
import com.portal.client.service.ServiceSerializable;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Page;

public interface BudgetCrudService extends ServiceSerializable, CrudService {

	Page<Budget> findAll(int page, int pageSize);

	void save(Budget budget, CustomerRepresentativeOrderForm ordersForm);

	Optional<Page<Budget>> findByCode(String code, int page, int pageSize);

	void checkBudgetState(Budget toCheck);

	Budget estimate(String customerCode, String customerStore, Set<ItemToFindPrice> itemsToEstimate)
			throws CustomerNotFoundException, ItemsNotFoundException;

	void update(Budget budget);
}
