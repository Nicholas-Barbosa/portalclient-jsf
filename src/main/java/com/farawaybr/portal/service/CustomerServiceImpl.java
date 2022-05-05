package com.farawaybr.portal.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.dto.CustomerPageDTO;
import com.farawaybr.portal.dto.SearchCustomerByCodeAndStoreDTO;
import com.farawaybr.portal.dto.ProductPriceTableWrapper.ProductPriceTableJsonData;
import com.farawaybr.portal.repository.CustomerRepository;
import com.farawaybr.portal.vo.Customer;
import com.farawaybr.portal.vo.CustomerProductPriceTable;

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

	@Override
	public boolean findPriceTable(Customer customer) {
		Optional<List<ProductPriceTableJsonData>> optional = customerRepository.findPriceTable(customer.getCode(),
				customer.getStore());
		optional.ifPresent(json -> {
			CustomerProductPriceTable priceTable = new CustomerProductPriceTable();
			priceTable.setCode(json.get(0).getCode());
			priceTable.setProducts(json.parallelStream().map(ProductPriceTableJsonData::getProduct)
					.collect(ConcurrentSkipListSet::new, Set::add, Set::addAll));
			customer.setPriceTable(priceTable);
		});
		return optional.isPresent();

	}

}
