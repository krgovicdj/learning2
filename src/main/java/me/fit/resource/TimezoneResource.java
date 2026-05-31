package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import me.fit.model.TimezoneInfo;
import me.fit.service.TimezoneService;

@Path("/getTimezoneByIP")
public class TimezoneResource {

    @Inject
    TimezoneService timezoneService;

    @GET
    public Response getStudentTimezone(@QueryParam("userId") Long userId) {
        try {
            TimezoneInfo info = timezoneService.fetchAndAssignTimezoneToStudent(userId);
            return Response.ok(info).build();
        } catch (jakarta.ws.rs.NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
