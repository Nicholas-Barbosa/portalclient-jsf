package com.portal.jaxrs.resource;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.portal.dto.BudgetJasperReportDTO;
import com.portal.dto.BudgetEstimateDTO.EstimatedItem;
import com.portal.dto.BudgetJasperReportDTO.CustomerJasperReportDTO;
import com.portal.jasper.service.BudgetReport;

@Path("/download")
public class DownloadResource {

	@Inject
	private BudgetReport budgetReport;

	@GET
	public Response download() {
		BudgetJasperReportDTO budgetDTO = new BudgetJasperReportDTO(new BigDecimal(12.99), new BigDecimal(20.98),
				new BigDecimal(90), new CustomerJasperReportDTO("Nicholas", "Hawaii", "Pipeline", "Hawaii", "82828373"),
				List.of(new EstimatedItem(new BigDecimal(10), new BigDecimal(10), "GX901", "AB001", new BigDecimal(10),
						20, new BigDecimal(10), new BigDecimal(10))));

		Response response = Response.ok().header("Content-Disposition", "inline;filename=budget.pdf")
				.header("Content-Type", "application/pdf").entity(budgetReport.toPdf(budgetDTO)).build();

		return response;
	}
}
