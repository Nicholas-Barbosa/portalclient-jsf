package com.portal.client.controller;

import java.io.Serializable;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.data.PageEvent;

import com.portal.client.export.FinancialBondsExporter;
import com.portal.client.service.FinancialBondsService;
import com.portal.client.ui.lazy.datamodel.FinancialTitleLazyDataModel;
import com.portal.client.ui.lazy.datamodel.LazyDataModelBase;
import com.portal.client.ui.lazy.datamodel.LazyPopulateUtils;
import com.portal.client.util.jsf.ServerApiExceptionFacesMessageHelper;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.vo.FinancialBondsPage;
import com.portal.client.vo.FinancialBondsPage.FinacialBondsDTO;

@Named
@RequestScoped
public class FinancialBondsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3811638445093267666L;
	private FinancialBondsService bondsService;
	private LazyDataModelBase<FinacialBondsDTO> titles;
	private ServerApiExceptionFacesMessageHelper externalExceptionHelper;
	private FinancialBondsExporter exporter;
	private int pagesToExport = 1;

	@Inject
	public FinancialBondsController(FinancialBondsService fiTitleService,
			ServerApiExceptionFacesMessageHelper externalExceptionHelper, FinancialBondsExporter exporter) {
		super();
		this.bondsService = fiTitleService;
		this.externalExceptionHelper = externalExceptionHelper;
	}

	public void export() {
		System.out.println("export " + titles);
		if (titles != null && titles.getCollection().size() <= 15) {
			System.out.println("call export one!");
		}
	}

	public void onPage(PageEvent event) {
		this.loadTitles(event.getPage() + 1);
	}

	public void loadTitles(int page) {

		if (titles == null)
			titles = new FinancialTitleLazyDataModel();

		try {
			Optional<FinancialBondsPage> optional = bondsService.find(page, 15);
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

	public void setPagesToExport(int pagesToExport) {
		this.pagesToExport = pagesToExport;
	}
}
