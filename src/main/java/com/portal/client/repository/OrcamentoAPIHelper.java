package com.portal.client.repository;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.security.APIManager;
import com.portal.client.security.api.ServerAPI;

@ApplicationScoped
public class OrcamentoAPIHelper implements Serializable, APIHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6759109065591573681L;

	private ServerAPI orcamentoAPI;
	private final String orcamentoAPIKey = "ORCAMENTO_API";
	private APIManager apiManager;

	public OrcamentoAPIHelper() {
		// TODO Auto-generated constructor stub
	}

	@Inject
	public OrcamentoAPIHelper(APIManager apiManager) {
		super();
		this.orcamentoAPI = apiManager.getAPI(orcamentoAPIKey);
		this.apiManager = apiManager;
	}

	@Override
	public ServerAPI getSourceAPI() {
		return orcamentoAPI;
	}

	@Override
	public String getSourceAPIKey() {
		return orcamentoAPIKey;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return orcamentoAPI.getToken();
	}

	@Override
	public String buildEndpoint(String string) {
		// TODO Auto-generated method stub
		return apiManager.buildEndpoint(orcamentoAPI, string);
	}

	@Override
	public String getPrefixToken() {
		// TODO Auto-generated method stub
		return orcamentoAPI.getTokenPrefix();
	}

	@Override
	public String getBasePath() {
		// TODO Auto-generated method stub
		return orcamentoAPI.getBasePath();
	}

}
