package com.portal.client.repository;

import java.io.Serializable;
import java.util.Optional;

import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerPageDTO;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;

public interface CustomerRepository extends Serializable {

	CustomerPageDTO find(int page, int pageSize);

	Optional<Customer> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO);

	Optional<CustomerPageDTO> findByName(String name, int page, int pageSize);
}
