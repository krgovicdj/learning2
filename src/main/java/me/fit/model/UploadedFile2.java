package me.fit.model;

import jakarta.persistence.*;

import java.io.File;
@Entity
public class UploadedFile2 {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    @Transient
    private File file;

    public UploadedFile2(String filename) {
        this.filename = filename;
    }

    public UploadedFile2() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
