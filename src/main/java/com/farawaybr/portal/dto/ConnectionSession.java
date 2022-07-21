package com.farawaybr.portal.dto;

import java.time.LocalDateTime;
import java.util.Locale;

import com.farawaybr.portal.osiip.IpInfo;
import com.farawaybr.portal.security.api.ProtheusApiEnviroment;

public class ConnectionSession implements Comparable<ConnectionSession> {

	private final String id, user, rawIp, userAgent;
	private final Locale locale;
	private final LocalDateTime startedAt;
	private final ProtheusApiEnviroment env;
	private IpInfo ip;

	public ConnectionSession(String id, String user, String rawIp, String userAgent, Locale locale,
			ProtheusApiEnviroment env) {
		super();
		this.id = id;
		this.user = user;
		this.rawIp = rawIp;
		this.userAgent = userAgent;
		this.locale = locale;
		this.startedAt = LocalDateTime.now();
		this.env = env;
	}

	public ConnectionSession(String id, String user, String rawIp, String userAgent, Locale locale,
			ProtheusApiEnviroment env, IpInfo ip) {
		this(id, user, rawIp, userAgent, locale, env);
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public String getRawIp() {
		return rawIp;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public Locale getLocale() {
		return locale;
	}

	public LocalDateTime getStartedAt() {
		return startedAt;
	}

	public ProtheusApiEnviroment getEnv() {
		return env;
	}

	public IpInfo getIp() {
		return ip;
	}

	public void setIp(IpInfo ip) {
		this.ip = ip;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectionSession other = (ConnectionSession) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConnectionSession [id=" + id + ", user=" + user + ", ip=" + rawIp + ", userAgent=" + userAgent
				+ ", locale=" + locale + ", startedAt=" + startedAt + ", env=" + env + "]";
	}

	@Override
	public int compareTo(ConnectionSession o) {
		// TODO Auto-generated method stub
		return o.startedAt.compareTo(startedAt);
	}

}
