package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.model.Lesson;
import me.fit.service.FileUploadService;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@Path("/files")
public class FileUploadResource {

    @Inject
    FileUploadService fileUploadService;

    @POST
    @Path("/upload/lesson/{lessonId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFileToLesson(
            @PathParam("lessonId") Long lessonId,
            @FormParam("file") FileUpload fileUpload) {

        try {
            Lesson updatedLesson = fileUploadService.uploadFileToLesson(lessonId, fileUpload);
            return Response.ok(updatedLesson).build();

        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Upload error: " + e.getMessage())
                    .build();
        }
    }
    @GET
    @Path("/lesson/{lessonId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLessonWithFiles(@PathParam("lessonId") Long lessonId) {
        try {
            Lesson lesson = fileUploadService.getLessonWithFiles(lessonId);
            return Response.ok(lesson).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}