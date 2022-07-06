package com.farawaybr.portal.dto;

import javax.validation.constraints.NotEmpty;

import com.farawaybr.portal.security.api.ProtheusApiEnviroment;

public class LoginProtheusForm {

	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	private ProtheusApiEnviroment environment;

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

	public ProtheusApiEnviroment getEnvironment() {
		return environment;
	}

	public void setEnvironment(ProtheusApiEnviroment environment) {
		this.environment = environment;
	}

	@Override
	public String toString() {
		return "LoginProtheusForm [username=" + username + ", password=" + password + ", companyEnv=" + environment
				+ "]";
	}

}
