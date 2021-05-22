package com.portal.jsf.primefaces;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response;

import org.primefaces.PrimeFaces;

public class PrimeFHelper {

	private PrimeFHelper() {

	}

	public static void openClientErrorExceptionView(Response response) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("response", response);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("fitViewport", true);
		PrimeFaces.current().dialog().openDynamic("response", options, null);
	}
}
