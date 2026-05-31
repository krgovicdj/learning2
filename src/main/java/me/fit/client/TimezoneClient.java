package me.fit.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import me.fit.model.TimezoneResponse;

@Path("/api/time/current/ip")
@RegisterRestClient(configKey = "time-api")
public interface TimezoneClient {

    @GET
    TimezoneResponse getTimezoneByIp(@QueryParam("ipAddress") String ip);
}