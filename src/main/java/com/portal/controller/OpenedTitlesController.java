package com.portal.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import com.portal.dto.FinancialTitlePageDTO.FinacialTitleDTO;
import com.portal.service.FinancialTitleService;
import com.portal.ui.lazy.datamodel.FinancialTitleLazyDataModel;
import com.portal.ui.lazy.datamodel.LazyPopulateUtils;

@Named
@RequestScoped
public class OpenedTitlesController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3811638445093267666L;
	@Inject
	private FinancialTitleService fiTitleService;
	private LazyDataModel<FinacialTitleDTO> titles;

	public void loadTitles(int page) {
		titles = new FinancialTitleLazyDataModel();
		LazyPopulateUtils.populate(titles, fiTitleService.find(page, 10));
	}

	public LazyDataModel<FinacialTitleDTO> getTitles() {
		return titles;
	}
}
