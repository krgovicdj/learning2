package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.model.Lesson;
import me.fit.model.MultipartRequest;
import me.fit.model.Profile;
import me.fit.model.UploadedFile2;
import me.fit.service.FileUploadService;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Path("/files")
public class FileUploadResource {
    @Inject
    EntityManager em;
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

    @POST
    @Path("/profile/uploadFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response uploadFileToProfile(MultipartRequest mr,@QueryParam("profileId")Long id)throws IOException {
        try {
            Profile p = em.find(Profile.class,id);
            if (p==null){
                throw new NotFoundException("Profile not found!");
            }
            java.nio.file.Path path=Paths.get("uploads/profiles/");
            if(!Files.exists(path)){
                Files.createDirectories(path);
            }
            java.nio.file.Path fileName=path.resolve(mr.file.fileName());
            UploadedFile2 up2=new UploadedFile2(fileName.toString());
            if(Files.exists(fileName)){
                System.out.println("File already exists!");
            }
            em.persist(up2);
            p.getUploadedfiles().add(up2);
            em.merge(p);

            Files.copy(mr.file.uploadedFile(),path.resolve(mr.file.fileName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }
}