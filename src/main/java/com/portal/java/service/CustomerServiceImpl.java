package com.portal.java.service;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.java.dto.CustomerDTO;
import com.portal.java.dto.CustomerPageDTO;
import com.portal.java.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.java.repository.CustomerRepository;

@ApplicationScoped
public class CustomerServiceImpl implements CustomerService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3405598320495650943L;

	private final CustomerRepository customerRepository;

	@Inject
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	public CustomerPageDTO findAll(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException,SocketException {
		// TODO Auto-generated method stub
		return customerRepository.find(page, pageSize);
	}

	@Override
	public Optional<CustomerDTO> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO)
			throws SocketTimeoutException, ConnectException, TimeoutException,SocketException {
		// TODO Auto-generated method stub
		return customerRepository.findByCodeAndStore(searchCustomerByCodeAndStoreDTO);
	}

	@Override
	public Optional<CustomerPageDTO> findByName(String name, int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException,SocketException {
		// TODO Auto-generated method stub
		return customerRepository.findByName(name, page, pageSize);
	}

}
