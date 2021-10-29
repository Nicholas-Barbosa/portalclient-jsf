package com.portal.client.security.api;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.resources.ConfigPropertyResolver;

@ApplicationScoped
public class ProtheusApiEnviromentHandler {

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
		if (enviroment.equals("prod") && comEnv == ProtheusCompanyApiEnv.CDG) {
			return propertiesResolver.getProperty(configProtheusUrlProdKey, "9990");
		}
		String baseUrl = propertiesResolver.getProperty(
				enviroment.equals("test") ? configProtheusUrlTestKey : configProtheusUrlProdKey, comEnv.getPort()+"");
		return baseUrl;
	}
}
