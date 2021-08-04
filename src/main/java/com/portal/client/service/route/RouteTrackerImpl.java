package com.portal.client.service.route;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;

import com.portal.client.service.route.Request.RequestB;

@SessionScoped
public class RouteTrackerImpl implements RouteTracker {

	private final Deque<Request> requests;

	public RouteTrackerImpl() {
		super();
		this.requests = new LinkedList<>();
	}

	@Override
	public void addRequest(HttpServletRequest servletRequest) {
		List<String> parameters = new ArrayList<>();
		servletRequest.getParameterNames().asIterator().forEachRemaining(s -> parameters.add(s));
		Request request = RequestB.getBuilder().withServlet(servletRequest).build();
		requests.offer(request);

	}

	@Override
	public Request getLastRequest() {
		// TODO Auto-generated method stub
		return requests.peekLast();
	}

	@Override
	public Request getFirstRequest() {
		// TODO Auto-generated method stub
		return requests.peekFirst();
	}

}
