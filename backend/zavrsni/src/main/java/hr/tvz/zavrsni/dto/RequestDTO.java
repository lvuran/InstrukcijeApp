package hr.tvz.zavrsni.dto;

import hr.tvz.zavrsni.model.enumeration.Subject;

public class RequestDTO {
    private Long teacherId;
    private Subject subject;
    private String description;


    public Long getTeacherId() {
        return teacherId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
