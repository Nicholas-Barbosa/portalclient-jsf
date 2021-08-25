package com.portal.client.controller.show;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.portal.client.util.jsf.FacesUtils;

@Named
@RequestScoped
public class CustomerSearchShowController implements ShowController<String> {

	public void show(String keyword) {
		Map<String, Object> options = Map.of("modal", true, "responsive", true,"contentWidth", "50vw","contentHeight", "55vh");
		Map<String, List<String>> queryParams = Map.of("keyword", List.of(keyword));
		FacesUtils.openViewOnDialog(options, "customerSearch", queryParams);
	}
}
