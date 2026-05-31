package me.fit.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import me.fit.client.IpClient;
import me.fit.client.TimezoneClient;
import me.fit.model.Student;
import me.fit.model.TimezoneInfo;
import me.fit.model.TimezoneResponse;
import java.time.LocalDateTime;

@ApplicationScoped
public class TimezoneService {

    @Inject
    EntityManager em;

    @Inject
    @RestClient
    IpClient ipClient;

    @Inject
    @RestClient
    TimezoneClient timezoneClient;

    @Transactional
    public TimezoneInfo fetchAndAssignTimezoneToStudent(Long studentId) {

        Student student = em.find(Student.class, studentId);
        if (student == null) {
            throw new NotFoundException("Student sa id-jem " + studentId + " nije pronađen");
        }

        String publicIp = ipClient.getPublicIp();
        System.out.println("Dobijena javna IP adresa: " + publicIp);

        TimezoneResponse tzResponse = timezoneClient.getTimezoneByIp(publicIp);
        System.out.println("Dobijena vremenska zona: " + tzResponse.getTimeZone());

        TimezoneInfo info = new TimezoneInfo();
        info.setTimeZone(tzResponse.getTimeZone());
        info.setCurrentLocalTime(tzResponse.getCurrentLocalTime());
        info.setCheckedAt(LocalDateTime.now().toString());
        info.setStudent(student);

        em.persist(info);
        student.getTimezoneInfos().add(info);
        em.merge(student);

        return info;
    }
}
