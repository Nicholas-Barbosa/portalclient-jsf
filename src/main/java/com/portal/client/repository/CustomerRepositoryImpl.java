package com.portal.client.repository;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;

import com.portal.client.client.rest.RestClient;
import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerPageDTO;
import com.portal.client.dto.NoPageCustomerResponseDTO;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.security.UserSessionAPIManager;
import com.portal.client.security.api.ServerAPI;

@ApplicationScoped
public class CustomerRepositoryImpl implements CustomerRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8042300828676622038L;
	private final RestClient restClient;
	private final UserSessionAPIManager apiManager;

	public CustomerRepositoryImpl() {
		this(null, null);
	}

	@Inject
	public CustomerRepositoryImpl(RestClient restClient, UserSessionAPIManager endpointBuilder) {
		super();
		this.restClient = restClient;
		this.apiManager = endpointBuilder;
	}

	@Override
	public CustomerPageDTO find(int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		// TODO Auto-generated method stub
		Map<String, Object> queryParms = new HashMap<>();
		queryParms.put("page", page);
		queryParms.put("pageSize", pageSize);
		ServerAPI api = apiManager.getAPI("ORCAMENTO_API");
		CustomerPageDTO customerPage = restClient.get(apiManager.buildEndpoint("ORCAMENTO_API", "client"),
				api.getToken(), api.getTokenPrefix(), CustomerPageDTO.class, queryParms, null,
				MediaType.APPLICATION_JSON);
		return customerPage;

	}

	@Override
	public Optional<Customer> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		try {
			Map<String, Object> pathParams = getMapInstance();
			pathParams.put("code", searchCustomerByCodeAndStoreDTO.getCode());
			pathParams.put("codeStore", searchCustomerByCodeAndStoreDTO.getStore());
			ServerAPI serverAPI = apiManager.getAPI("ORCAMENTO_API");
			return Optional.of(restClient.get(apiManager.buildEndpoint(serverAPI, "clients/{code}/loja/{codeStore}"),
					serverAPI.getToken(), serverAPI.getTokenPrefix(), NoPageCustomerResponseDTO.class, null, pathParams,
					MediaType.APPLICATION_JSON).getClients().get(0));
		} catch (NotFoundException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<CustomerPageDTO> findByName(String name, int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException {
		try {
			Map<String, Object> queryParams = getMapInstance();
			queryParams.put("page", page);
			queryParams.put("pageSize", pageSize);
			queryParams.put("searchKey", name);
			ServerAPI serverAPI = apiManager.getAPI("ORCAMENTO_API");
			Optional<CustomerPageDTO> cPage = Optional.of(restClient.get(apiManager.buildEndpoint(serverAPI, "clients"),
					serverAPI.getToken(), serverAPI.getTokenPrefix(), CustomerPageDTO.class, queryParams, null,
					MediaType.APPLICATION_JSON));

			return cPage.get().getContent().size() > 0 ? cPage : Optional.empty();
		} catch (NotFoundException e) {
			System.out.println("Not found!");
			return Optional.empty();
		}

	}

	private Map<String, Object> getMapInstance() {
		return new HashMap<>();
	}

}
