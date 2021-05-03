package com.portal.repository;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.ProcessingException;

import com.portal.pojo.Customer;
import com.portal.pojo.CustomerPage;

public interface CustomerRepository extends Serializable {

	CustomerPage getAllByPage(int page, int pageSize) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, TimeoutException;

	Optional<Customer> getByCodeAndStore(String code, String store) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, TimeoutException;

}
