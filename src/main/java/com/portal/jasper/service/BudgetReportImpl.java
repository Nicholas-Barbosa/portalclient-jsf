package com.portal.jasper.service;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.portal.dto.BudgetJasperReportDTO;
import com.portal.jasper.ReportService;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Stateless
public class BudgetReportImpl implements BudgetReport {

	@EJB
	private ReportService reportService;

	public BudgetReportImpl() {
		// TODO Auto-generated constructor stub
	}

	public BudgetReportImpl(ReportService reportService) {
		super();
		this.reportService = reportService;
	}

	@Override
	public byte[] toPdf(BudgetJasperReportDTO budget) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<>();
		params.put("itemsCollection", new JRBeanCollectionDataSource(budget.getItems()));
		params.put("logoGaussPath", "C:\\Users\\nicho\\OneDrive\\Documentos\\JavaWorkspace\\JakartaEE Workspace\\PortalAppClient\\src\\main\\webapp\\resources\\gauss.png");
		return reportService.exportToPdf(getClass().getResourceAsStream("/report/budgetEstimate.jasper"), params,
				budget);
	}

}
