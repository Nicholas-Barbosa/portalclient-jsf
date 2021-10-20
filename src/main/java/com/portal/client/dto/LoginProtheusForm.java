package com.portal.client.dto;

import javax.validation.constraints.NotEmpty;

import com.portal.client.security.api.ProtheusCompanyApiEnv;

public class LoginProtheusForm {

	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	private ProtheusCompanyApiEnv companyEnv;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ProtheusCompanyApiEnv getCompanyEnv() {
		return companyEnv;
	}

	public void setCompanyEnv(ProtheusCompanyApiEnv companyEnv) {
		this.companyEnv = companyEnv;
	}
}
