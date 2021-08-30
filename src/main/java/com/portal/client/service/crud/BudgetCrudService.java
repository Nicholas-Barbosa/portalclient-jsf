package com.portal.client.service.crud;

import java.util.Optional;
import java.util.Set;

import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.dto.ItemToFindPrice;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;
import com.portal.client.service.ServiceSerializable;
import com.portal.client.vo.Budget;

public interface BudgetCrudService extends ServiceSerializable, CrudService {

	BudgetPage findAll(int page, int pageSize);

	void save(Budget budget, CustomerRepresentativeOrderForm ordersForm);

	Optional<Budget> findByCode(String code);

	void checkBudgetState(Budget toCheck);

	Budget estimate(String customerCode, String customerStore, Set<ItemToFindPrice> itemsToEstimate)
			throws CustomerNotFoundException, ItemsNotFoundException;
}
