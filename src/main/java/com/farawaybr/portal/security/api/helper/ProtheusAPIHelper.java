package com.farawaybr.portal.security.api.helper;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.farawaybr.portal.security.api.APIsManager;
import com.farawaybr.portal.security.api.ProtheusApiData;
import com.farawaybr.portal.security.user.RepresentativeUser;

@SessionScoped
public class ProtheusAPIHelper implements Serializable, APIHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6759109065591573681L;

	private ProtheusApiData orcamentoAPI;
	private final String orcamentoAPIKey = "PROTHEUS_API";
	private APIsManager apisRepository;

	public ProtheusAPIHelper() {
		// TODO Auto-generated constructor stub
	}

	@Inject
	public ProtheusAPIHelper(APIsManager apiManager) {
		super();
		this.orcamentoAPI = apiManager.getAPI(orcamentoAPIKey);
		this.apisRepository = apiManager;
	}

	@Override
	public ProtheusApiData getData() {
		return orcamentoAPI;
	}

	@Override
	public String getKey() {
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
		return apisRepository.buildEndpoint(orcamentoAPI, string);
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
		return orcamentoAPI.getLoggedUser();
	}

}
