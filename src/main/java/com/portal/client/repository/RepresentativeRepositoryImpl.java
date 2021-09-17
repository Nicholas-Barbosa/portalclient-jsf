package com.portal.client.repository;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import com.portal.client.dto.RepresentativeAddtionalDataDTO;
import com.portal.client.dto.RepresentativeAddtionalDataWrapperDTO;
import com.portal.client.jaxrs.client.TokenedRestClient;
import com.portal.client.security.user.RepresentativeUser;

@ApplicationScoped
public class RepresentativeRepositoryImpl implements RepresentativeRepository, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3735726969594990824L;

	@Inject
	private TokenedRestClient restClient;

	@Inject
	private APIHelper orcamentoAPI;

	@Override
	public void getAdditionalData() {
		// TODO Auto-generated method stub
		RepresentativeAddtionalDataDTO data = restClient.get(orcamentoAPI.buildEndpoint("representative"),
				orcamentoAPI.getToken(), orcamentoAPI.getPrefixToken(), RepresentativeAddtionalDataWrapperDTO.class,
				null, null, MediaType.APPLICATION_JSON).getData();
		RepresentativeUser user = (RepresentativeUser) orcamentoAPI.getUser();
		user.setCode(data.getCode());
		user.setFantasyName(data.getFantasyname());
		user.setName(data.getName());
		user.setEmail(data.getEmail());
	}

}
