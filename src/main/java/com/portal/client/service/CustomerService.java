package com.portal.client.service;

import java.util.Optional;

import com.portal.client.dto.CustomerPageDTO;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.vo.Customer;

public interface CustomerService extends ServiceSerializable {

	CustomerPageDTO findAll(int page, int pageSize);

	Optional<Customer> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO);

	Optional<CustomerPageDTO> findByName(String name, int page, int pageSize);
	
	boolean findPriceTable(Customer customer);
}
