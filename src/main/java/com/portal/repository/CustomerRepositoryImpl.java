package com.portal.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;

import com.portal.cdi.qualifier.OAuth2RestAuth;
import com.portal.client.rest.auth.AuthenticatedRestClient;
import com.portal.dto.CustomerDTO;
import com.portal.dto.CustomerPageDTO;
import com.portal.dto.NoPageCustomerResponseDTO;
import com.portal.dto.SearchCustomerByCodeAndStoreDTO;

@ApplicationScoped
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
	public CustomerPageDTO getAllByPage(int page, int pageSize) throws ProcessingException {
		// TODO Auto-generated method stub
		Map<String, Object> queryParms = new HashMap<>();
		queryParms.put("page", page);
		queryParms.put("pageSize", pageSize);

		CustomerPageDTO customerPage = restClient.get("ORCAMENTO_API", "clients", CustomerPageDTO.class,
				queryParms, null, MediaType.APPLICATION_JSON);
		return customerPage;

	}

	@Override
	public Optional<CustomerDTO> getByCodeAndStore(SearchCustomerByCodeAndStoreDTO searchCustomerByCodeAndStoreDTO)
			throws ProcessingException {
		try {
			Map<String, Object> pathParams = getMapInstance();
			pathParams.put("code", searchCustomerByCodeAndStoreDTO.getCode());
			pathParams.put("codeStore", searchCustomerByCodeAndStoreDTO.getStore());
			return Optional
					.of(restClient
							.get("ORCAMENTO_API", "clients/{code}/loja/{codeStore}",
									NoPageCustomerResponseDTO.class, null, pathParams, MediaType.APPLICATION_JSON)
							.getClients().get(0));
		} catch (NotFoundException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<CustomerPageDTO> getByName(String name, int page, int pageSize) throws ProcessingException {
		try {
			Map<String, Object> queryParams = getMapInstance();
			queryParams.put("page", page);
			queryParams.put("pageSize", pageSize);
			queryParams.put("searchKey", name);
			Optional<CustomerPageDTO> cPage = Optional.of(restClient.get("ORCAMENTO_API", "clients",
					CustomerPageDTO.class, queryParams, null, MediaType.APPLICATION_JSON));

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
