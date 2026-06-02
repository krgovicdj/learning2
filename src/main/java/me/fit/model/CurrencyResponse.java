package me.fit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyResponse {
    private String from;
    private String to;
    private double rate;
    private double value;
    private double convertedValue;

    public CurrencyResponse() {
    }

    public CurrencyResponse( String from, String to, double rate, double value, double convertedValue) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        this.value = value;
        this.convertedValue = convertedValue;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getConvertedValue() {
        return convertedValue;
    }

    public void setConvertedValue(double convertedValue) {
        this.convertedValue = convertedValue;
    }
}
