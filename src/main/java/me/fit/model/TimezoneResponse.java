package me.fit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimezoneResponse {

    private String timeZone;
    private String currentLocalTime;
    private String ipAddress;

    public TimezoneResponse() {}

    public TimezoneResponse(String timeZone, String currentLocalTime, String ipAddress) {
        this.timeZone = timeZone;
        this.currentLocalTime = currentLocalTime;
        this.ipAddress = ipAddress;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCurrentLocalTime() {
        return currentLocalTime;
    }

    public void setCurrentLocalTime(String currentLocalTime) {
        this.currentLocalTime = currentLocalTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
