package com.portal.client.controller;

import java.io.Serializable;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;

import org.primefaces.event.data.PageEvent;

import com.portal.client.dto.FinancialBondsPage;
import com.portal.client.dto.FinancialBondsPage.FinacialBondsDTO;
import com.portal.client.export.FinancialBondsExporter;
import com.portal.client.service.crud.FinancialBondsService;
import com.portal.client.ui.lazy.datamodel.FinancialTitleLazyDataModel;
import com.portal.client.ui.lazy.datamodel.LazyBehaviorDataModel;
import com.portal.client.ui.lazy.datamodel.LazyPopulatorUtils;
import com.portal.client.util.jsf.FacesUtils;
import com.portal.client.util.jsf.ProcessingExceptionFacesMessageHelper;

@Named
@RequestScoped
public class FinancialBondsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3811638445093267666L;
	private FinancialBondsService bondsService;
	private LazyBehaviorDataModel<FinacialBondsDTO> titles;
	private ProcessingExceptionFacesMessageHelper prossExcpetionShowMsg;
	private FinancialBondsExporter exporter;
	private int pagesToExport = 1;

	@Inject
	public FinancialBondsController(FinancialBondsService fiTitleService,
			ProcessingExceptionFacesMessageHelper externalExceptionHelper, FinancialBondsExporter exporter) {
		super();
		this.bondsService = fiTitleService;
		this.prossExcpetionShowMsg = externalExceptionHelper;
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
				LazyPopulatorUtils.populate(titles, f);
			}, () -> {
				FacesUtils.error(null, "Nenhum título encontrado", null, "growl");
			});

		} catch (ProcessingException e) {
			prossExcpetionShowMsg.displayMessage(e, null);
		}
	}

	public LazyBehaviorDataModel<FinacialBondsDTO> getTitles() {
		return titles;
	}

	public int getPagesToExport() {
		return pagesToExport;
	}

	public void setPagesToExport(int pagesToExport) {
		this.pagesToExport = pagesToExport;
	}
}
