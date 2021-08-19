package com.portal.client.service.crud;

import java.util.Optional;
import java.util.Set;

import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.BudgetPage;
import com.portal.client.dto.CustomerRepresentativeOrderForm;
import com.portal.client.dto.ItemToFindPrice;
import com.portal.client.exception.CustomerNotFoundException;
import com.portal.client.exception.ItemsNotFoundException;
import com.portal.client.service.ServiceSerializable;

public interface BudgetCrudService extends ServiceSerializable, CrudService {

	BudgetPage findAll(int page, int pageSize);

	void save(BaseBudget budget, CustomerRepresentativeOrderForm ordersForm);

	Optional<BaseBudget> findByCode(String code);

	void checkBudgetState(BaseBudget toCheck);

	BaseBudget estimate(String customerCode, String customerStore, Set<ItemToFindPrice> itemsToEstimate)
			throws CustomerNotFoundException, ItemsNotFoundException;
}
