package com.farawaybr.portal.security.servlet.filter;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

public class FacesAjaxFilter implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3661199264801613675L;

	@Override
	public void afterPhase(PhaseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Before phase");
		FacesContext currentRequest = FacesContext.getCurrentInstance();
		PartialViewContext partialView = currentRequest.getPartialViewContext();
		HttpServletResponse response = (HttpServletResponse) currentRequest.getExternalContext().getResponse();
		if (partialView.isAjaxRequest()) {
			String redirectHedaer = response.getHeader("redirect-to-login");
			if (redirectHedaer != null) {
				try {
					ExternalContext ec = currentRequest.getExternalContext();
					ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
					ec.setResponseStatus(401);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RESTORE_VIEW;
	}

}
