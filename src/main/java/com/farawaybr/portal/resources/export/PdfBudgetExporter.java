package com.farawaybr.portal.resources.export;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.farawaybr.portal.cdi.qualifier.PDF;
import com.farawaybr.portal.dto.BudgetJasperData;
import com.farawaybr.portal.dto.BudgetJasperForm;
import com.farawaybr.portal.resources.export.jasper.BudgetReport;
import com.farawaybr.portal.resources.export.jasper.service.JasperReportType;
import com.farawaybr.portal.security.api.helper.ProtheusAPIHelper;
import com.farawaybr.portal.security.user.InternalRepresentativeUser;
import com.farawaybr.portal.security.user.RepresentativeUser;
import com.farawaybr.portal.vo.Budget;

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
