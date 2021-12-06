package com.portal.client.resources.export;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.cdi.qualifier.PDF;
import com.portal.client.dto.BudgetJasperData;
import com.portal.client.dto.BudgetJasperForm;
import com.portal.client.resources.export.jasper.BudgetReport;
import com.portal.client.resources.export.jasper.service.JasperReportType;
import com.portal.client.security.api.helper.ProtheusAPIHelper;
import com.portal.client.security.user.InternalRepresentativeUser;
import com.portal.client.security.user.RepresentativeUser;
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
		RepresentativeUser user = protheusApi.getUser();
		data.setRepresentative(user.getName());
		String userType = user instanceof InternalRepresentativeUser
				? ((InternalRepresentativeUser) user).getLoggedSaleType().toString()
				: user.getType().toString();
		data.setRepresentativeType(userType);
		BudgetJasperForm form = new BudgetJasperForm(protheusApi.getUser().getType(), data);
		return budgetReport.generate(form, JasperReportType.PDF);
	}

}
