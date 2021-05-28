package com.portal.jaxrs.resource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.portal.dto.BudgetJasperReportDTO;
import com.portal.dto.BudgetJasperReportDTO.CustomerJasperReportDTO;
import com.portal.dto.EstimatedItem;
import com.portal.jasper.service.BudgetReport;

@Path("/download")
public class DownloadResource {

	@Inject
	private BudgetReport budgetReport;

	@GET
	public Response download() {
		final EstimatedItem item = new EstimatedItem(new BigDecimal(135.98), new BigDecimal(900),
				new BigDecimal(135.98), new BigDecimal(900), new BigDecimal(18.89), new BigDecimal(12.89),
				new BigDecimal(0), 0, 234);

		BudgetJasperReportDTO budgetDTO = new BudgetJasperReportDTO(new BigDecimal(12.99), new BigDecimal(20.98),
				new BigDecimal(49.530000000000001136868377216160297393798828125),
				new CustomerJasperReportDTO("Nicholas", "Hawaii", "Pipeline", "Hawaii", "82828373"), Set.of(item));

		Response response = Response.ok().header("Content-Disposition", "inline;filename=budget.pdf")
				.header("Content-Type", "application/pdf").entity(budgetReport.toPdf(budgetDTO)).build();

		return response;
	}

	@GET
	@Path("/base64")
	public Response downloadBase64() {
		try (InputStream in = new BufferedInputStream(
				new FileInputStream("C:\\Users\\nicho\\OneDrive\\Documentos\\filledReports\\budget.pdf"))) {
			return Response.ok().entity(Base64.getEncoder().encode(in.readAllBytes()))
					.header("Content-Type", "application/pdf").build();
		} catch (IOException e) {
			return Response.status(500).build();
		}
	}
}
