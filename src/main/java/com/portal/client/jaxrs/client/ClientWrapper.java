package com.portal.client.jaxrs.client;

import javax.ws.rs.client.Client;

public class ClientWrapper {

	private boolean inUse;

	private Client client;

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
	}

	public Client getClient() {
		return client;
	}

}
