package com.farawaybr.portal.resources.export;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.cdi.qualifier.Excel;
import com.farawaybr.portal.dto.BudgetJasperData;
import com.farawaybr.portal.dto.BudgetJasperForm;
import com.farawaybr.portal.resources.export.jasper.BudgetReport;
import com.farawaybr.portal.resources.export.jasper.service.JasperReportType;
import com.farawaybr.portal.security.api.helper.ProtheusAPIHelper;
import com.farawaybr.portal.vo.Budget;

@ApplicationScoped
@Excel
public class ExcelBudgetExporter implements BudgetExporter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5357786599377852612L;

	@Inject
	private BudgetReport budgetReport;

	@Inject
	private ProtheusAPIHelper protheusApi;

	@Override
	public byte[] export(Budget budget) {
		BudgetJasperData data = new BudgetJasperData(budget);
		data.setRepresentative(protheusApi.getUser().getName());
		data.setRepresentativeType(protheusApi.getUser().getType().toString());
		BudgetJasperForm form = new BudgetJasperForm(protheusApi.getUser().getType(), data);
		return budgetReport.generate(form, JasperReportType.EXCEL);
	}

}
