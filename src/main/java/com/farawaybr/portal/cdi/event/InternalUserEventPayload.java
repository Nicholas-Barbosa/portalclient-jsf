package com.farawaybr.portal.cdi.event;

public class InternalUserEventPayload {

	private boolean isInternal;

	public InternalUserEventPayload( boolean isInternal) {
		super();
		this.isInternal = isInternal;
	}

	public boolean isInternal() {
		return isInternal;
	}
}
