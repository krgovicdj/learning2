package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.model.Course;
import me.fit.model.Student;
import me.fit.service.StudentService;

import java.util.List;

@Path("/student")
public class StudentResource {
    @Inject
    private StudentService studentService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addStudent(Student student) {
        studentService.createStudent(student);
        return Response.ok(student).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAll")
    public Response getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return Response.ok(students).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getStudentById(@PathParam("id") Long id) {
        return  Response.ok(studentService.getStudentById(id)).build();
    }

    @POST
    @Path("/{studentId}/addCourse/{courseId}")
    @Transactional
    public Response addCourseToStudent(@PathParam("studentId") Long studentId,
                                       @PathParam("courseId") Long courseId) {
        Student student = studentService.addCourseToStudent(studentId, courseId);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(student).build();
    }

    @GET
    @Path("/{studentId}/courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoursesByStudentId(@PathParam("studentId") Long studentId) {
        List<Course> courses = studentService.getCoursesByStudentId(studentId);
        return Response.ok(courses).build();
    }

}
