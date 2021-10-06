package com.portal.client.controller.show;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.portal.client.util.jsf.FacesUtils;

@Named
@RequestScoped
public class ProductSiteIframeShowController implements ShowController<String> {

	@Override
	public void show(String p) {
		System.out.println("p " +p);
		Map<String, Object> options = Map.of("modal", true, "responsive", true, "contentWidth", "87vw", "contentHeight",
				"85vh");
		Map<String, List<String>> queryParams = Map.of("url", List.of(p));
		FacesUtils.openViewOnDialog(options, "/face/productSiteIframe", queryParams);
	}

}
