package com.portal.client.jaxrs.server.resource;

import java.util.Arrays;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.portal.client.dto.GenericResponse.ResponseField;
import com.portal.client.dto.BaseBudget;
import com.portal.client.dto.GenericResponse;
import com.portal.client.dto.ItemBudgetToEstimate;
import com.portal.client.repository.BudgetRepository;
import com.portal.client.vo.BudgetEstimatedResultSet;

@Path("budget")
public class BudgetResource {

	private BudgetRepository repository;

	@Inject
	public BudgetResource(BudgetRepository repository) {
		super();
		this.repository = repository;
	}

	@GET
	@Path("{productCode}")
	@Produces("application/json")
	public Response estimate(@PathParam("productCode") String productCode) {
		System.out.println("estimate");
		try {
			BudgetEstimatedResultSet budgetResultSet = repository.estimate("002421", "01",
					Set.of(new ItemBudgetToEstimate(productCode, 10)));

			if (budgetResultSet.isOk()) {
				BaseBudget baseBudget = budgetResultSet.getBudget();
				ResponseField field = new ResponseField("total", baseBudget.getLiquidValue());
				return Response.ok()
						.entity(new GenericResponse(200, Set.of(field), "Estimated object has been created")).build();
			} else {
				if (!budgetResultSet.getError().isOkWithItems()) {
					return Response.status(400)
							.entity(new GenericResponse(400, null,
									"There is a problem with products! "
											+ Arrays.toString(budgetResultSet.getError().getItemsWithErrors())))
							.build();
				}
				return Response.status(400)
						.entity(new GenericResponse(400, null,
								"There is a problem with client! " + budgetResultSet.getError().getCustomerError()))
						.build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Fodeu ").build();
		}
	}
}
