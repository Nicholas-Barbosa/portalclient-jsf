package com.portal.jasper.service;

import java.net.URISyntaxException;
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
	public byte[] export(BudgetJasperReportDTO budget, String type) {
		switch (type.toUpperCase()) {
		case "PDF":
			return toPdf(budget);

		case "EXCEL":
			return toExcel(budget);
		default:
			throw new IllegalArgumentException("Invalid type. Onlye PDF and EXCEL");
		}
	}

	@Override
	public byte[] toPdf(BudgetJasperReportDTO budget) {
		// TODO Auto-generated method stub

		Map<String, Object> params = new HashMap<>();
		params.put("itemsCollection", new JRBeanCollectionDataSource(budget.getItems()));
		try {
			params.put("logoGaussPath", getClass().getResource("/report/images/gauss.png").toURI().getPath());
			return reportService.exportToPdf(getClass().getResourceAsStream("/report/budgetEstimate.jasper"), params,
					budget);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte[] toExcel(BudgetJasperReportDTO budget) {
		Map<String, Object> params = new HashMap<>();
		params.put("itemsCollection", new JRBeanCollectionDataSource(budget.getItems()));
		try {
			params.put("logoGaussPath", getClass().getResource("/report/images/gauss.png").toURI().getPath());
			return reportService.exportToExcel(getClass().getResourceAsStream("/report/budgetEstimate.jasper"), params,
					budget);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
