package me.fit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String bio;
    @ManyToMany
    @JsonIgnore
    private List<UploadedFile2> uploadedfiles=new ArrayList<>();

    public List<UploadedFile2> getUploadedfiles() {
        return uploadedfiles;
    }

    public void setUploadedfiles(List<UploadedFile2> uploadedfiles) {
        this.uploadedfiles = uploadedfiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Profile() {
    }
}
