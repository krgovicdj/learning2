package me.fit.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import me.fit.model.CurrencyResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
@Path("/api/rates")
@RegisterRestClient(configKey = "currency-api")
public interface CurrencyClient {
    @GET
    CurrencyResponse getRates(@QueryParam("from") String from, @QueryParam("to") String to);

}
