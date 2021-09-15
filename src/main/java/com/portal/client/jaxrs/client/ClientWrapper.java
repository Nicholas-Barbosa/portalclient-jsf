package com.portal.client.jaxrs.client;

import java.time.LocalDateTime;

import javax.ws.rs.client.Client;

public final class ClientWrapper {

	private boolean inUse;

	private final Client client;

	private LocalDateTime lastUsed;

	public ClientWrapper(boolean inUse, Client client) {
		super();
		this.inUse = inUse;
		this.client = client;
	}

	public synchronized boolean isInUse() {
		return inUse;
	}

	public synchronized void setInUse(boolean inUse) {
		this.inUse = inUse;
		if (!inUse) {
			lastUsed = LocalDateTime.now();
		}
	}

	public Client getClient() {
		return client;
	}

	public void closeClient() {
		this.client.close();
	}
	public LocalDateTime getLastUsed() {
		return lastUsed;
	}
}
