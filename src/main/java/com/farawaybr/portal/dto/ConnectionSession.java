package com.farawaybr.portal.dto;

import java.time.LocalDate;
import java.util.Locale;

public class ConnectionSession {

	private final String id, user, ip, userAgent;
	private final Locale locale;
	private final LocalDate startedAt;

	public ConnectionSession(String id, String user, String ip, String userAgent, Locale locale, LocalDate startedAt) {
		super();
		this.id = id;
		this.user = user;
		this.ip = ip;
		this.userAgent = userAgent;
		this.locale = locale;
		this.startedAt = startedAt;
	}

	public String getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public String getIp() {
		return ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public Locale getLocale() {
		return locale;
	}

	public LocalDate getStartedAt() {
		return startedAt;
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

	
}
