package com.farawaybr.portal.dto;

import java.util.Locale;

import com.farawaybr.portal.security.api.ProtheusApiEnviroment;

public class ConnectionSessionBuilder {

	private String id, user, ip, userAgent;
	private Locale locale;
	private ProtheusApiEnviroment env;

	public static ConnectionSessionBuilder getInstance() {
		return new ConnectionSessionBuilder();
	}

	private ConnectionSessionBuilder() {
		// TODO Auto-generated constructor stub
	}

	public ConnectionSessionBuilder withId(String id) {
		this.id = id;
		return this;
	}

	public ConnectionSessionBuilder withUser(String user) {
		this.user = user;
		return this;
	}

	public ConnectionSessionBuilder withIp(String ip) {
		this.ip = ip;
		return this;
	}

	public ConnectionSessionBuilder withUserAgent(String userAgent) {
		this.userAgent = userAgent;
		return this;
	}

	public ConnectionSessionBuilder withLocale(Locale locale) {
		this.locale = locale;
		return this;
	}

	public ConnectionSessionBuilder withEnvironment(ProtheusApiEnviroment env) {
		this.env = env;
		return this;
	}

	public ConnectionSession build() {
		return new ConnectionSession(id, user, ip, userAgent, locale, env);
	}
}
