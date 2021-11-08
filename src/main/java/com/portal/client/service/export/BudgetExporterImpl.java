package com.portal.client.service.export;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.BudgetJasperForm;
import com.portal.client.security.api.helper.ProtheusAPIHelper;
import com.portal.client.service.crud.OrderRprensentativeSetter;
import com.portal.client.service.export.jasper.BudgetJasperData;
import com.portal.client.service.export.jasper.BudgetReport;
import com.portal.client.service.export.jasper.JasperReportType;
import com.portal.client.vo.Budget;
@ApplicationScoped
public class BudgetExporterImpl implements BudgetExporter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5357786599377852612L;

	@Inject
	private BudgetReport orderReport;

	@Inject
	private OrderExcelCalculationCheck orderExcelCalculusConference;

	private OrderJsonHandler jsonHandler;

	@Inject
	private ProtheusAPIHelper protheusApi;

	@Inject
	private OrderRprensentativeSetter orderRepStter;

	@Override
	public byte[] export(Budget budget, OrderExportType type) {
		BudgetJasperData data = new BudgetJasperData(budget);
		data.setRepresentative(protheusApi.getUser().getName());
		BudgetJasperForm form = new BudgetJasperForm(protheusApi.getUser().getType(), data);
		if (budget.getRepresentative() == null)
			orderRepStter.setAutor(budget);
		switch (type) {
		case PDF:
			return orderReport.process(form, JasperReportType.PDF);
		case EXCEL:
			return orderReport.process(form, JasperReportType.EXCEL);
		case EXCEL_CALC_CONFERENCE:
			return orderExcelCalculusConference.createWorkbook(budget);
		case JSON:
			return jsonHandler.toJson(budget);
		}
		throw new IllegalArgumentException(type + " not supported");
	}

}
