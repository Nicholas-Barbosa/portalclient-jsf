package com.farawaybr.portal.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.security.api.ProtheusApiEnviroment;

@ApplicationScoped
public class ProtheusApiUrlResolver {

	@Inject
	private ConfigPropertiesResolver propertiesResolver;
	private final String configProtheusUrlTestKey = "protheus_test_env_url";
	private final String configProtheusUrlProdKey = "protheus_production_env_url";

	public String getUrl(ProtheusApiEnviroment comEnv) {
		if (propertiesResolver.getProfile().equals("prod") && comEnv == ProtheusApiEnviroment.CDG) {
			return propertiesResolver.getProperty(configProtheusUrlProdKey, "9990");
		}
		String baseUrl = propertiesResolver.getProperty(
				propertiesResolver.getProfile().equals("test") ? configProtheusUrlTestKey : configProtheusUrlProdKey,
				comEnv.getPort() + "");
		return baseUrl;
	}
}
