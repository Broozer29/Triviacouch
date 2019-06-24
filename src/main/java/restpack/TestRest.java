package restpack;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;


@Path("/TestRest")
@ApplicationPath("/service")
public class TestRest extends Application{
	
	@GET
	@Path("/sayhello")
	public String helloMSG() {
		return "Hello world";
	}
}
