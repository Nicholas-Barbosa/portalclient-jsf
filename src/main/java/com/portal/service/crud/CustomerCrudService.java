package com.portal.service.crud;

import javax.inject.Inject;

import com.portal.client.rest.RestClientTemplate;

public class CustomerCrudService implements CrudService {

	private final RestClientTemplate restClient;

	@Inject
	public CustomerCrudService(RestClientTemplate restClient) {
		super();
		this.restClient = restClient;
	}

	@Override
	public <T> T find(int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
