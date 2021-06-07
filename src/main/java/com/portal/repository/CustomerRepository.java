package com.portal.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.dto.CustomerDTO;
import com.portal.dto.CustomerPageDTO;
import com.portal.dto.SearchCustomerByCodeAndStoreDTO;

public interface CustomerRepository extends Serializable {

	CustomerPageDTO find(int page, int pageSize) throws SocketTimeoutException, ConnectException, TimeoutException;

	Optional<CustomerDTO> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO)
			throws SocketTimeoutException, ConnectException, TimeoutException;

	Optional<CustomerPageDTO> findByName(String name, int page, int pageSize)throws SocketTimeoutException, ConnectException, TimeoutException;
}
