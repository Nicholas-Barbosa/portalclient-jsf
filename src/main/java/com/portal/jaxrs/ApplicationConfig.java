package com.portal.jaxrs;

import java.util.HashMap;
import java.util.Map;

import javax.mvc.engine.ViewEngine;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {

	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(ViewEngine.VIEW_FOLDER, "/WebContent/");
		return properties;
	}
}
