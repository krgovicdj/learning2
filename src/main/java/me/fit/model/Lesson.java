package me.fit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
//@NamedQuery(name="Lesson.findAll",query = "select s from Lesson s")
//@NamedQuery(name ="Lesson.findById",query = "select s from Lesson s where id=:id")
@NamedQuery(name = Lesson.GET_ALL_LESSONS_FOR_COURSE_ID, query = "select s from Lesson s where s.course.id=:id")
public class Lesson {
    public static final String GET_ALL_LESSONS_FOR_COURSE_ID = "GetAllLessonsForCourseId";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @JsonIgnore
    private Course course;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "lesson_files",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private List<UploadedFile> uploadedFiles=new ArrayList<>();

    public List<UploadedFile> getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(List<UploadedFile> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Lesson() {
    }
}
