package com.portal.client.jaxrs.server.resource;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.portal.client.dto.ItemBudgetToEstimate;
import com.portal.client.repository.BudgetRepository;

@Path("budget")
public class BudgetResource {

	private BudgetRepository repository;

	@Inject
	public BudgetResource(BudgetRepository repository) {
		super();
		this.repository = repository;
	}

	@GET
	@Produces("application/json")
	public Response estimate() {
		try {
			repository.estimate("002421", "01", Set.of(new ItemBudgetToEstimate("GC4029", 10)));
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Fodeu ").build();
		}
	}
}
