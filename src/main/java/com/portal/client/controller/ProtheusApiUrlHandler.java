package com.portal.client.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.resources.ConfigPropertyResolver;
import com.portal.client.security.api.ProtheusCompanyApiEnv;

@ApplicationScoped
public class ProtheusApiUrlHandler {

	@Inject
	private ConfigPropertyResolver propertiesResolver;
	private final String configProtheusUrlTestKey = "protheus_test_env_url";
	private final String configProtheusUrlProdKey = "protheus_production_env_url";
	private String enviroment;

	@PostConstruct
	public void afterDI() {
		enviroment = propertiesResolver.getProperty("protheus_current_env");
	}

	public String getUrl(ProtheusCompanyApiEnv comEnv) {
		if (enviroment.equals("prod") && comEnv == ProtheusCompanyApiEnv.GAUSS) {
			return propertiesResolver.getProperty(configProtheusUrlProdKey, "9990");
		}
		String baseUrl = propertiesResolver.getProperty(
				enviroment.equals("test") ? configProtheusUrlTestKey : configProtheusUrlProdKey, comEnv.getPort()+"");
		return baseUrl;
	}
}
