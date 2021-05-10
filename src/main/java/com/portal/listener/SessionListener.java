package com.portal.listener;

import java.util.Locale;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener()
public class SessionListener implements HttpSessionListener {

	

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		Locale.setDefault(Locale.ENGLISH);
		se.getSession().setMaxInactiveInterval(1800000);
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("HttpSession will be destroyed!");

	}
}
