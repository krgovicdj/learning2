package me.fit.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import me.fit.model.CurrencyInfo;
import me.fit.service.CurrencyService;

@Path("/currencyConversion")
public class CurrencyResource {
    @Inject
    CurrencyService currencyService;

    @GET
    @RolesAllowed("admin")
    public Response getCurrencyConversion(@QueryParam("from") String from, @QueryParam("to") String to, @QueryParam("value") Double value,@QueryParam("userId") Long userId) {
        try {
            CurrencyInfo ci=currencyService.convertAndSave(userId,from,to,value);
            return Response.ok(ci).build();
        }catch (NotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }
}
