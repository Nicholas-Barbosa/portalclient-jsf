package com.portal.producer;

import java.util.Locale;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.ServletRequest;

public class LocaleProducer {

	private final ServletRequest servletRequest;

	@Inject
	public LocaleProducer(ServletRequest servletRequest) {
		super();
		this.servletRequest = servletRequest;
	}

	@Produces
	public Locale getInstance() {
		return servletRequest.getLocale();
	}
}
