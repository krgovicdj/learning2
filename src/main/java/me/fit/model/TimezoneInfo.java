package me.fit.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TimezoneInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String timeZone;
    private String currentLocalTime;
    private String checkedAt;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public TimezoneInfo() {}

    public TimezoneInfo(String timeZone, String currentLocalTime, String checkedAt, Student student) {
        this.timeZone = timeZone;
        this.currentLocalTime = currentLocalTime;
        this.checkedAt = checkedAt;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(String checkedAt) {
        this.checkedAt = checkedAt;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
