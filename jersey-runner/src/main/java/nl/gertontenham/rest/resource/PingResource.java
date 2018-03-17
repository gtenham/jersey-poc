package nl.gertontenham.rest.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ping")
public class PingResource {

    private static final Logger log = LoggerFactory.getLogger(PingResource.class);

    private String data = "pong!";

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public Response handlePing() {
        return Response.ok(data).build();
    }
}
