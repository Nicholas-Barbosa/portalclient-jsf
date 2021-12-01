package com.portal.client.security.api.register;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.portal.client.security.api.ProtheusCompanyApiEnv;
import com.portal.client.resources.ProtheusApiUrlResolver;
import com.portal.client.security.api.APIsRepository;
import com.portal.client.security.api.ApiData;
import com.portal.client.security.user.RepresentativeUser;

@Dependent
public class ProtheusApiRegisterImpl implements ProtheusApiRegister {

	private String token, prefix;
	private RepresentativeUser user;
	private ProtheusCompanyApiEnv companyEnv;
	private APIsRepository apisManger;
	private ProtheusApiUrlResolver urlResolver;

	@Inject
	public ProtheusApiRegisterImpl(APIsRepository apisManger, ProtheusApiUrlResolver protheusApiHandler) {
		super();
		this.apisManger = apisManger;
		this.urlResolver = protheusApiHandler;
	}

	@Override
	public ProtheusApiRegister token(String token) {
		this.token = token;
		return this;
	}

	@Override
	public ProtheusApiRegister tokenPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	@Override
	public ProtheusApiRegister companyEnv(ProtheusCompanyApiEnv companyEnv) {
		this.companyEnv = companyEnv;
		return this;
	}

	@Override
	public ApiData register() {
		// TODO Auto-generated method stub
		ApiData api = new ApiData(user, urlResolver.getUrl(companyEnv), "v1/token", token, prefix);
		api.setAttribute("companyEnv", companyEnv);
		apisManger.registerAuthenticatedService("PROTHEUS_API", api);
		return api;
	}

	@Override
	public ProtheusApiRegister setUser(RepresentativeUser user) {
		this.user = user;
		return this;
	}

}
