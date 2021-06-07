package com.portal.service;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.dto.CustomerDTO;
import com.portal.dto.CustomerPageDTO;
import com.portal.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.repository.CustomerRepository;

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
			throws SocketTimeoutException, ConnectException, TimeoutException {
		// TODO Auto-generated method stub
		return customerRepository.find(page, pageSize);
	}

	@Override
	public Optional<CustomerDTO> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO)
			throws SocketTimeoutException, ConnectException, TimeoutException {
		// TODO Auto-generated method stub
		return customerRepository.findByCodeAndStore(searchCustomerByCodeAndStoreDTO);
	}

	@Override
	public Optional<CustomerPageDTO> findByName(String name, int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException {
		// TODO Auto-generated method stub
		return customerRepository.findByName(name, page, pageSize);
	}

}
