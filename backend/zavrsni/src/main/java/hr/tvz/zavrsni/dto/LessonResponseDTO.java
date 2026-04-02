package hr.tvz.zavrsni.dto;

import hr.tvz.zavrsni.model.enumeration.Subject;
import hr.tvz.zavrsni.model.enumeration.Type;

import java.time.LocalDateTime;

public class LessonResponseDTO {
    Long lessonId;
    Long studentId;
    Long teacherID;
    String teacherName;
    String studentName;
    LocalDateTime dateTime;
    Subject subject;
    Type type;
    String description;
    Integer duration;
    Integer price;
    String info;
    Boolean seen;
    Boolean active;
    String address;
    Integer grade;


    public LessonResponseDTO(Long lessonId, Long studentId, Long teacherID, String teacherName, String studentName, LocalDateTime dateTime, Subject subject, Type type, String description, Integer duration, Integer price, String info, Boolean seen, Boolean active, String address, Integer grade) {
        this.lessonId = lessonId;
        this.studentId = studentId;
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.studentName = studentName;
        this.dateTime = dateTime;
        this.subject = subject;
        this.type = type;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.info = info;
        this.seen = seen;
        this.active = active;
        this.address = address;
        this.grade = grade;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Long getTeacherID() {
        return teacherID;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setTeacherID(Long teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
