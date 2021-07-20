package com.portal.client.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.vo.Customer;
import com.portal.client.vo.CustomerPageDTO;

public interface CustomerRepository extends Serializable {

	CustomerPageDTO find(int page, int pageSize) throws SocketTimeoutException, ConnectException, TimeoutException,SocketException;

	Optional<Customer> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO)
			throws SocketTimeoutException, ConnectException, TimeoutException,SocketException;

	Optional<CustomerPageDTO> findByName(String name, int page, int pageSize)throws SocketTimeoutException, ConnectException, TimeoutException,SocketException;
}
