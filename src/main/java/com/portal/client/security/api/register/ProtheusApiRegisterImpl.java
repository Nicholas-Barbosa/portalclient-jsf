package com.portal.client.security.api.register;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.portal.client.resources.ConfigPropertyResolver;
import com.portal.client.security.api.ProtheusCompanyApiEnv;

@ApplicationScoped
public class ProtheusApiRegisterImpl implements ProtheusApiRegister {

	private String token, prefix;
	private String env;
	private ProtheusCompanyApiEnv companyEnv;
	private ConfigPropertyResolver propertiesResolver;
	
	@PostConstruct
	public void init() {
		this.env = propertiesResolver.getProperty("protheus_current_env");
	}
	@Override
	public ApiRegister token(String token) {
		this.token = token;
		return this;
	}

	@Override
	public ApiRegister tokenPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiRegister companyEnv(ProtheusCompanyApiEnv companyEnv) {
		this.companyEnv = companyEnv;
		return this;
	}

}
