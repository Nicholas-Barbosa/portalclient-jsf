package com.farawaybr.portal.service.route;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;

@SessionScoped
public class RequestTrackerImpl implements RequestTracker, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8959483923993585901L;
	private final Deque<Request> requests;

	public RequestTrackerImpl() {
		super();
		this.requests = new LinkedList<>();
	}

	@Override
	public void addRequest(HttpServletRequest servletRequest) {
		if (servletRequest.getMethod().equals("GET")) {
			System.out.println("Add servlet request " + servletRequest);
			List<String> parameters = new ArrayList<>();
			servletRequest.getParameterNames().asIterator().forEachRemaining(s -> parameters.add(s));
			Request request = new Request(servletRequest);
			requests.offer(request);
		}
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

	@Override
	public List<Request> getAll() {
		// TODO Auto-generated method stub
		return requests.parallelStream().collect(CopyOnWriteArrayList::new, List::add, List::addAll);
	}

	@Override
	public boolean isEmpty() {
		return requests.isEmpty();
	}

}
