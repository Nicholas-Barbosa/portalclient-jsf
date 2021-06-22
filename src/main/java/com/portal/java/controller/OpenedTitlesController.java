package com.portal.java.controller;

import java.io.Serializable;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.ClientErrorException;

import org.primefaces.model.LazyDataModel;

import com.portal.java.dto.FinancialBondsPageDTO.FinacialBondsDTO;
import com.portal.java.service.FinancialBondsService;
import com.portal.java.ui.lazy.datamodel.FinancialTitleLazyDataModel;
import com.portal.java.ui.lazy.datamodel.LazyPopulateUtils;
import com.portal.java.util.jsf.ClientExceptionFacesUtils;

@Named
@RequestScoped
public class OpenedTitlesController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3811638445093267666L;
	@Inject
	private FinancialBondsService fiTitleService;
	private LazyDataModel<FinacialBondsDTO> titles;

	public void loadTitles(int page) {
		titles = new FinancialTitleLazyDataModel();
		try {
			LazyPopulateUtils.populate(titles, fiTitleService.find(page, 10));
		} catch (SocketTimeoutException | SocketException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientErrorException e) {
			ClientExceptionFacesUtils.openClientExcpetionView(e.getResponse());
		}
	}

	public LazyDataModel<FinacialBondsDTO> getTitles() {
		return titles;
	}
}
