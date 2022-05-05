package com.farawaybr.portal.service.route;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface RequestTracker {

	void addRequest(HttpServletRequest request);

	Request getLastRequest();

	Request getFirstRequest();

	List<Request> getAll();

	boolean isEmpty();
}
