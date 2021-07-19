package com.portal.java.controller;

import java.io.Serializable;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.java.dto.FinancialBondsPageDTO;
import com.portal.java.dto.FinancialBondsPageDTO.FinacialBondsDTO;
import com.portal.java.export.FinancialBondsExporter;
import com.portal.java.service.FinancialBondsService;
import com.portal.java.ui.lazy.datamodel.FinancialTitleLazyDataModel;
import com.portal.java.ui.lazy.datamodel.LazyDataModelBase;
import com.portal.java.ui.lazy.datamodel.LazyPopulateUtils;
import com.portal.java.util.jsf.ExternalServerExceptionFacesHelper;
import com.portal.java.util.jsf.FacesUtils;

@Named
@RequestScoped
public class FinancialBondsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3811638445093267666L;
	private FinancialBondsService bondsService;
	private LazyDataModelBase<FinacialBondsDTO> titles;
	private ExternalServerExceptionFacesHelper externalExceptionHelper;
	private FinancialBondsExporter exporter;
	private int pagesToExport = 1;

	@Inject
	public FinancialBondsController(FinancialBondsService fiTitleService,
			ExternalServerExceptionFacesHelper externalExceptionHelper, FinancialBondsExporter exporter) {
		super();
		this.bondsService = fiTitleService;
		this.externalExceptionHelper = externalExceptionHelper;
	}

	public void export() {
		if (titles != null && titles.getWrappedData().size() <= 15) {

		}
	}

	public void onPage(PageEvent event) {
		this.loadTitles(event.getPage() + 1);
	}

	public void loadTitles(int page) {

		if (titles == null)
			titles = new FinancialTitleLazyDataModel();

		try {
			Optional<FinancialBondsPageDTO> optional = bondsService.find(page, 15);
			optional.ifPresentOrElse(f -> {
				LazyPopulateUtils.populate(titles, f);
			}, () -> {
				FacesUtils.error(null, "Nenhum título encontrado", null, "growl");
			});

		} catch (SocketTimeoutException | SocketException | TimeoutException e) {
			externalExceptionHelper.displayMessage(e, null);
		}
	}

	public LazyDataModelBase<FinacialBondsDTO> getTitles() {
		return titles;
	}

	public int getPagesToExport() {
		return pagesToExport;
	}
}
