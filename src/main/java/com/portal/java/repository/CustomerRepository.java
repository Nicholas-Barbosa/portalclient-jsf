package com.portal.java.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.java.dto.Customer;
import com.portal.java.dto.CustomerPageDTO;
import com.portal.java.dto.SearchCustomerByCodeAndStoreDTO;

public interface CustomerRepository extends Serializable {

	CustomerPageDTO find(int page, int pageSize) throws SocketTimeoutException, ConnectException, TimeoutException,SocketException;

	Optional<Customer> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO)
			throws SocketTimeoutException, ConnectException, TimeoutException,SocketException;

	Optional<CustomerPageDTO> findByName(String name, int page, int pageSize)throws SocketTimeoutException, ConnectException, TimeoutException,SocketException;
}
