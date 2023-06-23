package apitest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class APIController {

	@Secured
	@POST
	@Path("/activate")
	public Response activate(String body) {
		// Request gets authenticated by AuthenticationFilter.filter (@Secured) before redirecting to API endpoint. 
		
		// Redirect to internal procedures
		return Functions.activate(body);
	}

	@GET
	@Path("/ping")
	public Response ping() {
		// No authentication for testing

		return Response.ok("Returned Ping").build();
	}
}