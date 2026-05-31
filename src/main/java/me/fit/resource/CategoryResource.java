package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.model.Category;
import me.fit.service.CategoryService;
import me.fit.service.CourseService;

import java.util.List;

@Path("/category")
public class CategoryResource {
    @Inject
    CourseService courseService;
    @Inject
    private CategoryService categoryService;

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory() {
        List<Category> categories = categoryService.getAllCategories();
        return Response.ok(categories).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryById(@PathParam("id") Long id) {
        return Response.ok(categoryService.getCategoryById(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response createCategory(Category category) {
        categoryService.createCategory(category);
        return Response.ok(category).build();
    }

    @GET
    @Path("/getByCourse/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoriesByCourseId(@PathParam("courseId") Long courseId) {
        List<Category> categories = categoryService.getCategoriesByCourseId(courseId);
        return Response.ok(categories).build();
    }
}
