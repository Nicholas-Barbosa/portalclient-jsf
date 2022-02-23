package com.portal.client.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.portal.client.dto.CustomerPageDTO;
import com.portal.client.dto.ProductPriceTableWrapper.ProductPriceTableJsonData;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.vo.Customer;

public interface CustomerRepository extends Serializable {

	CustomerPageDTO find(int page, int pageSize);

	Optional<Customer> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO);

	Optional<CustomerPageDTO> findByName(String name, int page, int pageSize);
	
	Optional<List<ProductPriceTableJsonData>> findPriceTable(String customerCode, String customerStore);
}
