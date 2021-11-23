package com.portal.client.security.api;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.resources.ConfigPropertyResolver;

@ApplicationScoped
public class ProtheusApiUrlResolver {

	@Inject
	private ConfigPropertyResolver propertiesResolver;
	private final String configProtheusUrlTestKey = "protheus_test_env_url";
	private final String configProtheusUrlProdKey = "protheus_production_env_url";

	public String getUrl(ProtheusCompanyApiEnv comEnv) {
		if (propertiesResolver.getProfile().equals("prod") && comEnv == ProtheusCompanyApiEnv.CDG) {
			return propertiesResolver.getProperty(configProtheusUrlProdKey, "9990");
		}
		String baseUrl = propertiesResolver.getProperty(
				propertiesResolver.getProfile().equals("test") ? configProtheusUrlTestKey : configProtheusUrlProdKey,
				comEnv.getPort() + "");
		return baseUrl;
	}
}
