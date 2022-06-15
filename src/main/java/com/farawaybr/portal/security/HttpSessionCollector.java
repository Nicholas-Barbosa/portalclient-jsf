package com.farawaybr.portal.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener("HttpSessionListener")
public class HttpSessionCollector implements HttpSessionListener {

	private static final Map<String, HttpSession> HTTP_SESSION = new ConcurrentHashMap<>();

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("Sessão criada! " + se.getSession().getId());
		HTTP_SESSION.put(se.getSession().getId(), se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		HTTP_SESSION.remove(se.getSession().getId());
	}

	public static HttpSession find(String jId) {
		return HTTP_SESSION.get(jId);
	}
}
