package com.portal.client.security.api;

public enum ProtheusCompanyApiEnv {

	CDG(8090), NSG(8094);

	private final int port;

	private ProtheusCompanyApiEnv(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

}
