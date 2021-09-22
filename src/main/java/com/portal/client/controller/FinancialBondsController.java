package com.portal.client.controller;

import java.io.Serializable;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.data.PageEvent;

import com.portal.client.dto.FinancialBondsPage;
import com.portal.client.dto.FinancialBondsPage.FinacialBondsDTO;
import com.portal.client.export.FinancialBondsExporter;
import com.portal.client.service.crud.BillsToReceiveService;
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
	private BillsToReceiveService bondsService;
	private LazyBehaviorDataModel<FinacialBondsDTO> titles;
	private ProcessingExceptionFacesMessageHelper prossExcpetionShowMsg;
	private FinancialBondsExporter exporter;
	private int recordsToExport;

	@Inject
	public FinancialBondsController(BillsToReceiveService fiTitleService,
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

		Optional<FinancialBondsPage> optional = bondsService.find(page, 15);
		optional.ifPresentOrElse(f -> {
			LazyPopulatorUtils.populate(titles, f);
			FacesUtils.executeScript("$('#noBillsFound').hide();$('#content').show()");
		}, () -> {
			FacesUtils.error(null, "Nenhum título encontrado", null, "growl");
			FacesUtils.executeScript("$('#noBillsFound').show();$('#content').hide()");
		});

	}

	public LazyBehaviorDataModel<FinacialBondsDTO> getTitles() {
		return titles;
	}

	public int getRecordsToExport() {
		return recordsToExport;
	}

	public void setRecordsToExport(int recordsToExport) {
		this.recordsToExport = recordsToExport;
	}

}
