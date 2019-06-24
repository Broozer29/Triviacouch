package restpack;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class TestRest {

	@GET
	public String helloMSG() {
		return "Hello world";
	}
}
