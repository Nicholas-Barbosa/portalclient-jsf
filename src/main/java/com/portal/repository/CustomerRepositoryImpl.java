package com.portal.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.dto.CustomerDTO;
import com.portal.dto.CustomerPageDTO;
import com.portal.dto.SearchCustomerByCodeAndStoreDTO;

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
	public CustomerPageDTO getAllByPage(int page, int pageSize) throws SocketTimeoutException, ConnectException,
			ProcessingException, IllegalArgumentException, TimeoutException, SocketException {
		// TODO Auto-generated method stub
		Map<String, Object> queryParms = new HashMap<>();
		queryParms.put("page", page);
		queryParms.put("pageSize", pageSize);

		CustomerPageDTO customerPage = restClient.getForEntity("ORCAMENTO_API", "clients", CustomerPageDTO.class,
				queryParms, null, MediaType.APPLICATION_JSON_TYPE);
		return customerPage;

	}

	@Override
	public Optional<CustomerDTO> getByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO)
			throws SocketTimeoutException, ConnectException, ProcessingException, IllegalArgumentException,
			TimeoutException, SocketException {
		try {
			Map<String, Object> pathParams = getMapInstance();
			pathParams.put("code", searchCustomerByCodeAndStoreDTO.getCode());
			pathParams.put("codeStore", searchCustomerByCodeAndStoreDTO.getStore());
			return Optional.of(restClient.getForEntity("ORCAMENTO_API", "clients/{code}/loja/{codeStore}",
					CustomerPageDTO.class, null, pathParams, MediaType.APPLICATION_JSON_TYPE).getClients().get(0));
		} catch (NotFoundException e) {
			System.out.println("Not found handler!");
			return Optional.empty();
		}
	}

	private Map<String, Object> getMapInstance() {
		return new HashMap<>();
	}

}
