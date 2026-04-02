package hr.tvz.zavrsni.model;

import hr.tvz.zavrsni.model.enumeration.Subject;
import hr.tvz.zavrsni.model.enumeration.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "LESSON")
public class Lesson
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STUDENT_ID", nullable = false)
    private Student student;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEACHER_ID", nullable = false)
    private Teacher teacher;



    private LocalDateTime dateTime;


    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private Subject subject;

    @Column
    private int duration;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private Type type;

    @Column
    private String location;

    @Column
    private Integer price;

    @Column
    private String description;

    @Column
    private String info;

    @Column
    private Boolean active;

    @Column
    private Boolean seen;

    public Lesson(Long id, Student student, Teacher teacher, LocalDateTime dateTime, Subject subject, int duration, Type type, String location, Integer price, String description, String info, Boolean active, Boolean seen) {
        this.id = id;
        this.student = student;
        this.teacher = teacher;
        this.dateTime = dateTime;
        this.subject = subject;
        this.duration = duration;
        this.type = type;
        this.location = location;
        this.price = price;
        this.description = description;
        this.info = info;
        this.active = active;
        this.seen = seen;
    }

    public Lesson() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
}
