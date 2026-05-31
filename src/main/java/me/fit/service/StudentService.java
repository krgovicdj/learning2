package me.fit.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.model.Course;
import me.fit.model.Student;

import java.util.ArrayList;
import java.util.List;

@Dependent
public class StudentService {
    @Inject
    EntityManager em;

    @Transactional
    public Student createStudent(Student student) {
        return em.merge(student);
    }

    @Transactional
    public List<Student> getAllStudents() {
        return em.createNamedQuery("Student.findAll", Student.class).getResultList();
    }

    @Transactional
    public Student getStudentById(Long id) {
        return em.createNamedQuery("Student.findById", Student.class).setParameter("id", id).getSingleResult();
    }

    @Transactional
    public Student addCourseToStudent(Long studentId, Long courseId) {
        Student student = em.find(Student.class, studentId);
        Course course = em.find(Course.class, courseId);

        if (student == null || course == null) {
            return null;
        }

        if (student.getCourses() == null) {
            student.setCourses(new ArrayList<>());
        }

        student.getCourses().add(course);
        return em.merge(student);
    }

    @Transactional
    public List<Course> getCoursesByStudentId(Long studentId) {
        return em.createQuery(
                        "SELECT c FROM Student s JOIN s.courses c WHERE s.id = :studentId",
                        Course.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }



}
