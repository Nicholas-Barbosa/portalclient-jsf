package com.portal.client.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.vo.Customer;
import com.portal.client.vo.CustomerPageDTO;

public interface CustomerService extends ServiceSerializable {

	CustomerPageDTO findAll(int page, int pageSize) throws SocketTimeoutException, ConnectException, TimeoutException,SocketException;

	Optional<Customer> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO)
			throws SocketTimeoutException, ConnectException, TimeoutException,SocketException;

	Optional<CustomerPageDTO> findByName(String name, int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException,SocketException;
}
