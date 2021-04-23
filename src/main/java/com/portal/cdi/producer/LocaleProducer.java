package com.portal.cdi.producer;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.ws.rs.Produces;

public class LocaleProducer {

	private final ServletRequest servletRequest;

	@Inject
	public LocaleProducer(ServletRequest servletRequest) {
		super();
		this.servletRequest = servletRequest;
	}

	
	@Produces
	public Locale getInstance() {
		System.out.println("getInstance");
		return servletRequest.getLocale();
	}
}
