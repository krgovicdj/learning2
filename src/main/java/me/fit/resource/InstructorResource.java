package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.model.Instructor;
import me.fit.service.InstructorService;

import java.util.List;

@Path("/instructor")
public class InstructorResource {
    @Inject
    private InstructorService instructorService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addInstructor(Instructor instructor) {
        instructorService.createInstructor(instructor);
        return Response.ok(instructor).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAll")
    public Response getAllInstructors() {
        List<Instructor> instructors = instructorService.getAllInstructors();
        return Response.ok(instructors).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getInstructorById(@PathParam("id") Long id) {
        return  Response.ok(instructorService.getInstructorById(id)).build();
    }
}
