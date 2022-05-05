package com.farawaybr.portal.service;

import java.util.Optional;

import com.farawaybr.portal.dto.CustomerPageDTO;
import com.farawaybr.portal.dto.SearchCustomerByCodeAndStoreDTO;
import com.farawaybr.portal.vo.Customer;

public interface CustomerService extends ServiceSerializable {

	CustomerPageDTO findAll(int page, int pageSize);

	Optional<Customer> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO);

	Optional<CustomerPageDTO> findByName(String name, int page, int pageSize);
	
	boolean findPriceTable(Customer customer);
}
