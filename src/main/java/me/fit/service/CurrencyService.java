package me.fit.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import me.fit.client.CurrencyClient;
import me.fit.model.CurrencyInfo;
import me.fit.model.CurrencyResponse;
import me.fit.model.Student;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class CurrencyService {
    @Inject
    EntityManager em;
    @Inject
    @RestClient
    CurrencyClient currencyClient;

    @Transactional
    public CurrencyInfo convertAndSave(Long userId, String from, String to, double value) {
        Student student = em.find(Student.class, userId);
        if (student == null) {
            throw new NotFoundException("User sa id-jem " + userId + " nije pronađen");
        }
        CurrencyResponse response = currencyClient.getRates(from, to);

        double convertedValue = value * response.getRate();

        response.setValue(value);
        response.setConvertedValue(convertedValue);

        CurrencyInfo info = new CurrencyInfo();
        info.setFromCurrency(from);
        info.setToCurrency(to);
        info.setRate(response.getRate());
        info.setValue(value);
        info.setConvertedValue(convertedValue);
        info.setStudent(student);

        em.persist(info);
        student.getCurrencies().add(info);
        em.merge(student);

        return info;
    }
}
