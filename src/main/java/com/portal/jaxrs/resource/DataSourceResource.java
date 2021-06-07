package com.portal.jaxrs.resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("dataSource")
public class DataSourceResource {

	@Resource(name = "jdbc/testPool")
	private DataSource dataSource;

	@GET
	public Response test() {
		try (Connection con = dataSource.getConnection();
				PreparedStatement sql = con.prepareStatement("SELECT * FROM state")) {
			ResultSet result = sql.executeQuery();
			List<String> objects = new ArrayList<>();
			while (result.next())
				objects.add(result.getString(3));
			return Response.ok(objects).build();
		} catch (SQLException e) {
			return Response.status(500).build();
		}
	}
}
