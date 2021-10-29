package com.portal.client.export;

import java.io.Serializable;

import javax.inject.Inject;

import com.portal.client.dto.BudgetJasperForm;
import com.portal.client.export.jasper.BudgetJasperData;
import com.portal.client.export.jasper.BudgetReport;
import com.portal.client.security.api.helper.ProtheusAPIHelper;
import com.portal.client.service.crud.OrderRprensentativeSetter;
import com.portal.client.vo.Budget;

public class BudgetExporterImpl implements BudgetExporter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5357786599377852612L;

	@Inject
	private BudgetReport orderReport;

	@Inject
	private OrderExcelCalculusConference orderExcelCalculusConference;

	private OrderJsonHandler jsonHandler;

	@Inject
	private ProtheusAPIHelper orcamentoAPI;

	@Inject
	private OrderRprensentativeSetter orderRepStter;

	@Override
	public byte[] export(Budget budget, OrderExportType type) {
		BudgetJasperForm form = new BudgetJasperForm(orcamentoAPI.getUser().getType(), new BudgetJasperData(budget));
		if (budget.getRepresentative() == null)
			orderRepStter.setAutor(budget);
			switch (type) {
			case PDF:
				return orderReport.export(form, type);
			case EXCEL:
				return orderReport.export(form, type);
			case EXCEL_CALC_CONFERENCE:
				return orderExcelCalculusConference.createWorkbook(budget);
			case JSON:
				return jsonHandler.toJson(budget);
			}
		throw new IllegalArgumentException(type + " not supported");
	}

}
