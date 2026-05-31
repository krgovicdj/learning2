package me.fit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name=Category.GET_ALL_CATEGORIES,query = "select s.id, s.name from Category s")
@NamedQuery(name="Category.findById",query = "select s.id, s.name from Category s where id=:id")
@NamedQuery(name = "Category.findByCourseId", query = "SELECT c FROM Category c JOIN c.courses co WHERE co.id = :courseId")
public class Category {
    public static final String GET_ALL_CATEGORIES = "GetAllCategories";
    public static final String GET_ALL_CATEGORIES_FOR_COURSE_ID = "GetAllCategoriesForCourseId";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private List<Course> courses = new ArrayList<>();

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category() {
    }
}
