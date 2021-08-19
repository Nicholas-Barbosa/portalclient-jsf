package com.portal.client.service;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerPageDTO;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.repository.CustomerRepository;

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
	public CustomerPageDTO findAll(int page, int pageSize) {
		// TODO Auto-generated method stub
		return customerRepository.find(page, pageSize);
	}

	@Override
	public Optional<Customer> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO) {
		// TODO Auto-generated method stub
		return customerRepository.findByCodeAndStore(searchCustomerByCodeAndStoreDTO);
	}

	@Override
	public Optional<CustomerPageDTO> findByName(String name, int page, int pageSize) {
		// TODO Auto-generated method stub
		return customerRepository.findByName(name, page, pageSize);
	}

}
