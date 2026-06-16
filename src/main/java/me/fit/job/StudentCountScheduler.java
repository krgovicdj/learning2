package me.fit.job;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class StudentCountScheduler {

    @Inject
    EntityManager em;

    @Scheduled(every = "60s")
    void logStudentCount() {
        Long studentCount = em.createQuery("SELECT COUNT(s) FROM Student s", Long.class).getSingleResult();
        System.out.println("[SCHEDULER] Students in dattabase: " + studentCount + " | time: " + java.time.LocalDateTime.now());
    }
}
