package hr.tvz.zavrsni.dto;

import hr.tvz.zavrsni.model.enumeration.Subject;
import hr.tvz.zavrsni.model.enumeration.Type;

import java.time.LocalDateTime;

public class LessonDTO {
    Long studentId;
    LocalDateTime dateTime;
    Subject subject;
    Type type;
    String description;

    public LessonDTO(Long studentId, LocalDateTime dateTime, Subject subject, Type type, String description) {
        this.studentId = studentId;
        this.dateTime = dateTime;
        this.subject = subject;
        this.type = type;
        this.description = description;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
