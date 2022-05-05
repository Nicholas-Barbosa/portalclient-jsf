package com.farawaybr.portal.security.auth;

import com.farawaybr.portal.security.api.ProtheusApiEnviroment;

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
