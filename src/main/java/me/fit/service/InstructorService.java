package me.fit.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.model.Instructor;

import java.util.List;

@Dependent
public class InstructorService {
    @Inject
    EntityManager em;
    @Transactional
    public Instructor createInstructor(Instructor instructor) {
        return em.merge(instructor);
    }
    @Transactional
    public List<Instructor> getAllInstructors() {
        return em.createNamedQuery("Instructor.findAll", Instructor.class).getResultList();
    }
    @Transactional
    public Instructor getInstructorById(Long id) {
        return em.createNamedQuery("Instructor.findById", Instructor.class).setParameter("id", id).getSingleResult();
    }
}
