package com.portal.client.security.api.helper;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.portal.client.security.APIManager;
import com.portal.client.security.api.ServerAPI;
import com.portal.client.security.user.RepresentativeUser;

@SessionScoped
public class ProtheusAPIHelper implements Serializable, APIHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6759109065591573681L;

	private ServerAPI orcamentoAPI;
	private final String orcamentoAPIKey = "PROTHEUS_API";
	private APIManager apiManager;

	public ProtheusAPIHelper() {
		// TODO Auto-generated constructor stub
	}

	@Inject
	public ProtheusAPIHelper(APIManager apiManager) {
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

	@Override
	public RepresentativeUser getUser() {
		// TODO Auto-generated method stub
		return (RepresentativeUser) orcamentoAPI.getUserData();
	}

	@Override
	public boolean isUserDataComplete() {
		return apiManager.isUserDataComplete(orcamentoAPIKey);
	}

}
