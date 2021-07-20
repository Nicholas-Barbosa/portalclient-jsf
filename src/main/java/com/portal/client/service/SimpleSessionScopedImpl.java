package com.portal.client.service;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class SimpleSessionScopedImpl implements SimpleSessionScopedBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3979122041801962796L;

	public void executeTask() {
		try {
			System.out.println("executing task...");
			Thread.sleep(2000);
			System.out.println("OK!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
	}
}
