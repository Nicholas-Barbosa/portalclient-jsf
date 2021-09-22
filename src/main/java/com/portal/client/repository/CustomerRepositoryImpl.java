package com.portal.client.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.Customer;
import com.portal.client.dto.CustomerPageDTO;
import com.portal.client.dto.NoPageCustomerResponseDTO;
import com.portal.client.dto.SearchCustomerByCodeAndStoreDTO;
import com.portal.client.jaxrs.client.TokenedRestClient;
import com.portal.client.repository.aop.OptionalEmptyRepository;
import com.portal.client.security.APIManager;
import com.portal.client.security.api.ServerAPI;

@ApplicationScoped
public class CustomerRepositoryImpl extends OptionalEmptyRepository implements CustomerRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8042300828676622038L;
	private final TokenedRestClient restClient;
	private final APIManager apiManager;

	public CustomerRepositoryImpl() {
		this(null, null);
	}

	@Inject
	public CustomerRepositoryImpl(TokenedRestClient restClient, APIManager endpointBuilder) {
		super();
		this.restClient = restClient;
		this.apiManager = endpointBuilder;
	}

	@Override
	public CustomerPageDTO find(int page, int pageSize) {
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
	public Optional<Customer> findByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO) {
		Map<String, Object> pathParams = getMapInstance();
		pathParams.put("code", searchCustomerByCodeAndStoreDTO.getCode());
		pathParams.put("codeStore", searchCustomerByCodeAndStoreDTO.getStore());
		ServerAPI serverAPI = apiManager.getAPI("ORCAMENTO_API");
		return Optional.of(restClient.get(apiManager.buildEndpoint(serverAPI, "clients/{code}/loja/{codeStore}"),
				serverAPI.getToken(), serverAPI.getTokenPrefix(), NoPageCustomerResponseDTO.class, null, pathParams,
				MediaType.APPLICATION_JSON).getClients().get(0));

	}

	@Override
	public Optional<CustomerPageDTO> findByName(String name, int page, int pageSize) {
		Map<String, Object> queryParams = getMapInstance();
		queryParams.put("page", page);
		queryParams.put("pageSize", pageSize);
		queryParams.put("searchKey", name);
		ServerAPI serverAPI = apiManager.getAPI("ORCAMENTO_API");
		Optional<CustomerPageDTO> cPage = Optional.of(restClient.get(apiManager.buildEndpoint(serverAPI, "clients"),
				serverAPI.getToken(), serverAPI.getTokenPrefix(), CustomerPageDTO.class, queryParams, null,
				MediaType.APPLICATION_JSON));

		return cPage.get().getContent().size() > 0 ? cPage : Optional.empty();

	}

	private Map<String, Object> getMapInstance() {
		return new HashMap<>();
	}

}
