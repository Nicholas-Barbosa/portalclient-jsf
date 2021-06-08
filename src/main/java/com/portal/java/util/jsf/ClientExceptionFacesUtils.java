package com.portal.java.util.jsf;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response;

public class ClientExceptionFacesUtils {

	private ClientExceptionFacesUtils() {

	}

	public static void openClientExcpetionView(Response response) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("response", response);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("fitViewport", true);
		options.put("position", "center");
		FacesUtils.openViewOnDialog(options, "clientError");
	}
}
