package com.portal.repository;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.dto.CustomerPageGaussDTO;
import com.portal.pojo.Customer;
import com.portal.pojo.CustomerPage;

public class CustomerRepositoryImpl implements CustomerRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8042300828676622038L;
	private final AuthenticatedRestClient restClient;

	public CustomerRepositoryImpl() {
		this(null);
	}

	@Inject
	public CustomerRepositoryImpl(@OAuth2RestAuth AuthenticatedRestClient restClient) {
		super();
		this.restClient = restClient;
	}

	@Override
	public CustomerPage getAllByPage(int page, int pageSize) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, TimeoutException {
		// TODO Auto-generated method stub
		Map<String, Object> queryParms = new HashMap<>();
		queryParms.put("page", page);
		queryParms.put("pageSize", pageSize);
		CustomerPageGaussDTO forEntity = restClient.getForEntity("ORCAMENTO_API", "clients", CustomerPageGaussDTO.class,
				queryParms, null, MediaType.APPLICATION_JSON_TYPE);
		return forEntity.toCustomerPage();
	}

	@Override
	public Optional<Customer> getByCodeAndStore(String code, String store) {
		// TODO Auto-generated method stub
		return null;
	}

}
