package com.farawaybr.portal.jsf.controller.components;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.farawaybr.portal.service.CustomerService;
import com.farawaybr.portal.vo.Customer;

@RequestScoped
@Named
public class CustomerDetailPanelController {

	@Inject
	private CustomerService customerService;
	
	public void load(Customer customer) {
		System.out.println("Customer " +customer);
//		if(customer.getCnpj() ==null) {
//			customerService.findByCodeAndStore(new SearchCustomerByCodeAndStoreDTO(customer.getCode(), customer.getStore())).ifPresent(full ->{
////				customer.
//			});;
		}
	}
