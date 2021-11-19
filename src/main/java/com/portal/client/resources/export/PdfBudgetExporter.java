package com.portal.client.resources.export;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.BudgetJasperForm;
import com.portal.client.resources.export.jasper.BudgetJasperData;
import com.portal.client.resources.export.jasper.BudgetReport;
import com.portal.client.resources.export.jasper.service.JasperReportType;
import com.portal.client.resources.export.jasper.service.annt.PDF;
import com.portal.client.security.api.helper.ProtheusAPIHelper;
import com.portal.client.vo.Budget;

@ApplicationScoped
@PDF
public class PdfBudgetExporter implements BudgetExporter, Serializable {

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
		BudgetJasperForm form = new BudgetJasperForm(protheusApi.getUser().getType(), data);
		return budgetReport.process(form, JasperReportType.PDF);
	}

}
