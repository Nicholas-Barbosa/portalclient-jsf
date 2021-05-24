package com.portal.jasper.service;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import com.portal.dto.BudgetJasperReportDTO;
import com.portal.http.ContentType;
import com.portal.jasper.ReportService;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Singleton
public class BudgetReportImpl implements BudgetReport {

	@EJB
	private ReportService reportService;

	private final String GAUSS_LOGO = getLogos("GAUSS");
	private final String CDG_LOGO = getLogos("CDG");

	public BudgetReportImpl() {
		// TODO Auto-generated constructor stub
	}

	public BudgetReportImpl(ReportService reportService) {
		super();
		this.reportService = reportService;
	}

	@Override
	public byte[] export(BudgetJasperReportDTO budget, ContentType type) {
		switch (type) {
		case PDF:
			return toPdf(budget);

		case EXCEL:
			return toExcel(budget);
		default:
			throw new IllegalArgumentException("Invalid type. Only PDF and EXCEL are supported by this service!");
		}
	}

	@Override
	public byte[] toPdf(BudgetJasperReportDTO budget) {
		// TODO Auto-generated method stub

		Map<String, Object> params = new HashMap<>();
		params.put("itemsCollection", new JRBeanCollectionDataSource(budget.getItems()));

		params.put("logoGaussPath", CDG_LOGO);
		return reportService.exportToPdf(getClass().getResourceAsStream("/report/budgetEstimate.jasper"), params,
				budget);

	}

	@Override
	public byte[] toExcel(BudgetJasperReportDTO budget) {
		Map<String, Object> params = new HashMap<>();
		params.put("itemsCollection", new JRBeanCollectionDataSource(budget.getItems()));
		params.put("logoGaussPath", GAUSS_LOGO);
		return reportService.exportToExcel(getClass().getResourceAsStream("/report/budgetEstimate.jasper"), params,
				budget);

	}

	private String getLogos(String logo) {
		try {
			switch (logo) {
			case "GAUSS":
				return getClass().getResource("/report/images/gauss.png").toURI().getPath();
			case "CDG":
				return getClass().getResource("/report/images/logocdg.png").toURI().getPath();
			default:
				throw new IllegalArgumentException("log does not exists!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
