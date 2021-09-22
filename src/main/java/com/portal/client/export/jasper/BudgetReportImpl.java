package com.portal.client.export.jasper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.portal.client.export.OrderExportType;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Singleton
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BudgetReportImpl implements BudgetReport {

	@EJB
	private JasperService reportService;

	private final String GAUSS_LOGO = getLogos("GAUSS");
	private final String CDG_LOGO = getLogos("CDG");

	public BudgetReportImpl() {
		// TODO Auto-generated constructor stub
	}

	public BudgetReportImpl(JasperService reportService) {
		super();
		this.reportService = reportService;
	}

	@Override
	public byte[] export(BudgetJasper budget, OrderExportType type) {
		Map<String, Object> params = this.configureLayout();
		params.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
		params.put("itemsCollection", new JRBeanCollectionDataSource(budget.getItems())); 
		switch (type) {
		case PDF:
			return reportService.exportToPdf(getClass().getResourceAsStream("/report/budgetEstimate.jasper"), params,
					budget);

		case EXCEL:
			return reportService.exportToExcel(getClass().getResourceAsStream("/report/budgetEstimate.jasper"), params,
					budget);
		default:
			throw new IllegalArgumentException("Invalid type. Only PDF and EXCEL are supported by this service!");
		}
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

	public Map<String, Object> configureLayout() {
		Map<String, Object> params = new HashMap<>();

		params.put("logoGaussPath", GAUSS_LOGO);
		params.put("title", "Gauss Indústria");
		return params;
	}
}
