package me.fit.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.model.Course;
import me.fit.model.Lesson;
import me.fit.model.Student;
import me.fit.service.CategoryService;
import me.fit.service.CourseService;

import java.util.List;

@Path("/course")
public class CourseResource {
    @Inject
    private CourseService courseService;
    @Inject
    private CategoryService categoryService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    @RolesAllowed("admin")
    public Response addCourse(Course course) {
        courseService.createCourse(course);
        return Response.ok(course).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAll")
    public Response getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return Response.ok(courses).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getCourseById(@PathParam("id") Long id) {
        return Response.ok(courseService.getCourseById(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getLessonsByCourseId")
    public Response getLessonsByCourseId(@QueryParam("id") Long id) {
        List<Lesson> lessons = courseService.getLessonsByCourseId(id);
        return Response.ok(lessons).build();
    }

    @POST
    @Path("/{courseId}/addCategory/{categoryId}")
    @Transactional
    public Response addCategoryToCourse(@PathParam("courseId") Long courseId,
                                        @PathParam("categoryId") Long categoryId) {
        Course course = courseService.addCategoryToCourse(courseId, categoryId);
        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Course or Category not found")
                    .build();
        }
        return Response.ok(course).build();
    }

    @GET
    @Path("/getByCategory/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoursesByCategoryId(@PathParam("categoryId") Long categoryId) {
        List<Course> courses = courseService.getCoursesByCategoryId(categoryId);
        return Response.ok(courses).build();
    }

    @GET
    @Path("/{courseId}/students")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentsByCourseId(@PathParam("courseId") Long courseId) {
        List<Student> students = courseService.getStudentsByCourseId(courseId);
        return Response.ok(students).build();
    }

}
