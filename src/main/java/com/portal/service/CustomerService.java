package com.portal.service;

import java.util.Optional;

import javax.ws.rs.ProcessingException;

import com.portal.dto.CustomerDTO;
import com.portal.dto.CustomerPageDTO;
import com.portal.dto.SearchCustomerByCodeAndStoreDTO;

public interface CustomerService extends ServiceSerializable{

	CustomerPageDTO findAll(int page, int pageSize) throws ProcessingException;

	Optional<CustomerDTO> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO)
			throws ProcessingException;

	Optional<CustomerPageDTO> findByName(String name, int page, int pageSize) throws ProcessingException;
}
