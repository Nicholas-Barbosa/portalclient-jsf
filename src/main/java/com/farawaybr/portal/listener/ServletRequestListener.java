package com.farawaybr.portal.listener;

import javax.inject.Inject;
import javax.servlet.ServletRequestEvent;
import javax.servlet.annotation.WebListener;

import com.farawaybr.portal.service.ResourceBundleService;

@WebListener()
public class ServletRequestListener implements javax.servlet.ServletRequestListener {

	@Inject
	private ResourceBundleService resourceService;

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		resourceService.init(sre.getServletRequest().getLocale());

	}
}
