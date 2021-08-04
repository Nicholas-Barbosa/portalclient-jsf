package com.portal.client.service.route;

import javax.servlet.http.HttpServletRequest;

public interface RouteTracker {

	void addRequest(HttpServletRequest request);

	Request getLastRequest();

	Request getFirstRequest();
}
