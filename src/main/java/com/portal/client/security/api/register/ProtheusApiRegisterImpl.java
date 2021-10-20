package com.portal.client.security.api.register;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.security.APIManager;
import com.portal.client.security.api.ProtheusApiEnviromentHandler;
import com.portal.client.security.api.ProtheusCompanyApiEnv;
import com.portal.client.security.api.ServerAPI;
import com.portal.client.security.user.RepresentativeUser;

@ApplicationScoped
public class ProtheusApiRegisterImpl implements ProtheusApiRegister {

	private String token, prefix;
	private RepresentativeUser user;
	private ProtheusCompanyApiEnv companyEnv;
	private APIManager apisManger;
	private ProtheusApiEnviromentHandler protheusApiHandler;

	@Inject
	public ProtheusApiRegisterImpl(APIManager apisManger, ProtheusApiEnviromentHandler protheusApiHandler) {
		super();
		this.apisManger = apisManger;
		this.protheusApiHandler = protheusApiHandler;
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
	public ServerAPI register() {
		// TODO Auto-generated method stub
		ServerAPI api = new ServerAPI(user, protheusApiHandler.getUrl(companyEnv), "v1/token", token, prefix);
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
