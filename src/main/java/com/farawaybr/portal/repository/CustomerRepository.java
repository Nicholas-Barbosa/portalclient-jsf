package com.farawaybr.portal.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.farawaybr.portal.dto.CustomerPageDTO;
import com.farawaybr.portal.dto.SearchCustomerByCodeAndStoreDTO;
import com.farawaybr.portal.dto.ProductPriceTableWrapper.ProductPriceTableJsonData;
import com.farawaybr.portal.vo.Customer;

public interface CustomerRepository extends Serializable {

	CustomerPageDTO find(int page, int pageSize);

	Optional<Customer> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO);

	Optional<CustomerPageDTO> findByName(String name, int page, int pageSize);
	
	Optional<List<ProductPriceTableJsonData>> findPriceTable(String customerCode, String customerStore);
}
