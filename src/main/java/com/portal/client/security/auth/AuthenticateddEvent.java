package com.portal.client.security.auth;

import com.portal.client.security.api.ProtheusApiEnviroment;

public class AuthenticateddEvent {

	private ProtheusApiEnviroment protheusEnviroment;

	public AuthenticateddEvent(ProtheusApiEnviroment protheusEnviroment) {
		super();
		this.protheusEnviroment = protheusEnviroment;
	}

	public ProtheusApiEnviroment getProtheusEnviroment() {
		return protheusEnviroment;
	}
}
