package hr.tvz.zavrsni.dto;

import hr.tvz.zavrsni.model.enumeration.Subject;

public class StudentRequestDTO {
    private Long id;
    private Subject subject;
    private String description;
    private Long personId;
    private String person;
    private Boolean active;
    private Integer grade;

    public StudentRequestDTO(Long id, Subject subject, String description, Long personId, String person, Boolean active, Integer grade) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.personId = personId;
        this.person = person;
        this.active = active;
        this.grade = grade;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public StudentRequestDTO() {
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
