package apitest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class TestService {

	@POST
	@Path("/activate")
	public Response activate(String body) {
		// Authorization
		
		// Redirect to internal procedures
		return Functions.activate(body);
	}
	
	@GET
	@Path("/ping")
	public Response ping() {
		return Response.ok("Returned Ping").build();
	}
}