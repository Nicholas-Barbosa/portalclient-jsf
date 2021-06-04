package com.portal.repository;

import java.io.Serializable;
import java.util.Optional;

import javax.ws.rs.ProcessingException;

import com.portal.dto.CustomerDTO;
import com.portal.dto.CustomerPageDTO;
import com.portal.dto.SearchCustomerByCodeAndStoreDTO;

public interface CustomerRepository extends Serializable {

	CustomerPageDTO getAllByPage(int page, int pageSize);

	Optional<CustomerDTO> getByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO)
			throws ProcessingException;

	Optional<CustomerPageDTO> getByName(String name, int page, int pageSize);
}
