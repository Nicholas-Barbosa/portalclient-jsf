package com.portal.client.security.auth;

import com.portal.client.security.api.ProtheusCompanyApiEnv;

public class AuthenticateddEvent {

	private ProtheusCompanyApiEnv protheusEnviroment;

	public AuthenticateddEvent(ProtheusCompanyApiEnv protheusEnviroment) {
		super();
		this.protheusEnviroment = protheusEnviroment;
	}

	public ProtheusCompanyApiEnv getProtheusEnviroment() {
		return protheusEnviroment;
	}
}
