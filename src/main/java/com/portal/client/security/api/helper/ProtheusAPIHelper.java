package com.portal.client.security.api.helper;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.portal.client.security.api.APIsRepository;
import com.portal.client.security.api.ApiData;
import com.portal.client.security.user.RepresentativeUser;

@SessionScoped
public class ProtheusAPIHelper implements Serializable, APIHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6759109065591573681L;

	private ApiData orcamentoAPI;
	private final String orcamentoAPIKey = "PROTHEUS_API";
	private APIsRepository apiManager;

	public ProtheusAPIHelper() {
		// TODO Auto-generated constructor stub
	}

	@Inject
	public ProtheusAPIHelper(APIsRepository apiManager) {
		super();
		this.orcamentoAPI = apiManager.getAPI(orcamentoAPIKey);
		this.apiManager = apiManager;
	}

	@Override
	public ApiData getSourceAPI() {
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
	public String getTokenPrefix() {
		// TODO Auto-generated method stub
		return orcamentoAPI.getTokenPrefix();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return orcamentoAPI.getBaseUrl();
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
