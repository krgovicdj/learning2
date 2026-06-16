package me.fit.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import me.fit.model.Lesson;
import me.fit.model.Profile;
import me.fit.model.UploadedFile;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@ApplicationScoped
public class FileUploadService {
    private static final String UPLOAD_DIRECTORY = "uploads/lessons/";
    private static final String UPLOAD_DIRECTORY2 = "uploads/profiles/";
    @Inject
    EntityManager em;
    @Transactional
    public Lesson uploadFileToLesson(Long lessonId, FileUpload fileUpload) throws IOException {
        Lesson lesson = em.find(Lesson.class, lessonId);
        if (lesson == null) {
            throw new NotFoundException("Lesson with id " + lessonId + " does not exist");
        }
        Path uploadPath= Paths.get(UPLOAD_DIRECTORY);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName=fileUpload.fileName();
        Path filePath = uploadPath.resolve(originalFileName);
        String savedFilename=filePath.toString();

        if (Files.exists(filePath)) {
            UploadedFile existingFile = findUploadedFileByFilename(savedFilename);
            if (existingFile != null && !lesson.getUploadedFiles().contains(existingFile)) {
                lesson.getUploadedFiles().add(existingFile);
                em.merge(lesson);
            }
            return lesson;
        }
        Files.copy(fileUpload.uploadedFile(), filePath, StandardCopyOption.REPLACE_EXISTING);
        UploadedFile uploadedFile = new UploadedFile(savedFilename);
        em.persist(uploadedFile);

        lesson.getUploadedFiles().add(uploadedFile);

        return em.merge(lesson);
    }

    private UploadedFile findUploadedFileByFilename(String filename) {
        try {
            return em.createQuery("select uf from UploadedFile uf where uf.filename=:filename",UploadedFile.class).setParameter("filename", filename).getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    @Transactional
    public Lesson getLessonWithFiles(Long lessonId){
        Lesson lesson = em.find(Lesson.class, lessonId);
        if (lesson == null) {
            throw new NotFoundException("Lesson with id " + lessonId + " does not exist");
        }

        for (UploadedFile uploadedFile : lesson.getUploadedFiles()) {
            File file=new File(uploadedFile.getFilename());
            if (file.exists()) {
                uploadedFile.setFile(file);
            }
        }
        return lesson;
    }

}
