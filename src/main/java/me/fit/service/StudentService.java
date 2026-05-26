package me.fit.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import me.fit.model.Student;

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


}
