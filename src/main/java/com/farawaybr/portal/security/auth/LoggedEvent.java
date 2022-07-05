package com.farawaybr.portal.security.auth;

import com.farawaybr.portal.security.api.ProtheusApiEnviroment;

public class LoggedEvent {

	private ProtheusApiEnviroment protheusEnviroment;

	public LoggedEvent(ProtheusApiEnviroment protheusEnviroment) {
		super();
		this.protheusEnviroment = protheusEnviroment;
	}

	public ProtheusApiEnviroment getProtheusEnviroment() {
		return protheusEnviroment;
	}
}
