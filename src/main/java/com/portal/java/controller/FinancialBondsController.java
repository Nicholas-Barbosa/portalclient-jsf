package com.portal.java.controller;

import java.io.Serializable;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.LazyDataModel;

import com.portal.java.dto.FinancialBondsPageDTO;
import com.portal.java.dto.FinancialBondsPageDTO.FinacialBondsDTO;
import com.portal.java.service.FinancialBondsService;
import com.portal.java.ui.lazy.datamodel.FinancialTitleLazyDataModel;
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
	private LazyDataModel<FinacialBondsDTO> titles;
	private ExternalServerExceptionFacesHelper externalExceptionHelper;

	@Inject
	public FinancialBondsController(FinancialBondsService fiTitleService,
			ExternalServerExceptionFacesHelper externalExceptionHelper) {
		super();
		this.bondsService = fiTitleService;
		this.externalExceptionHelper = externalExceptionHelper;
	}

	public List<Integer> skeleton() {
		return Stream.iterate(1, i -> i <= 10, i -> i + 1).collect(Collectors.toList());
	}

	public void onPage(PageEvent event) {
		this.loadTitles(event.getPage() + 1);
	}

	public void loadTitles(int page) {
		if (titles == null)
			titles = new FinancialTitleLazyDataModel();
		try {
			Optional<FinancialBondsPageDTO> optional = bondsService.find(page, 10);
			optional.ifPresentOrElse(f -> {
				LazyPopulateUtils.populate(titles, f);
			}, () -> {
				FacesUtils.error(null, "Nenhum título encontrado", null);
				PrimeFaces.current().ajax().update("growl");
			});

		} catch (SocketTimeoutException | SocketException | TimeoutException e) {
			externalExceptionHelper.displayMessage(e, null);
		}
	}

	public LazyDataModel<FinacialBondsDTO> getTitles() {
		return titles;
	}
}
