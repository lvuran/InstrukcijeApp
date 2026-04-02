package hr.tvz.zavrsni.model;

import hr.tvz.zavrsni.model.enumeration.Subject;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STUDENT")
public class Student{
    @Id
    private Long id;

    @OneToOne
    @MapsId
    private AppUser user;

    @Column
    private Integer grade;


 @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)

 private List<Lesson> lessons = new ArrayList<>();

 @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)

 private List<Request> requests = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id")
    )
 @Enumerated(EnumType.STRING)
 @Column(name = "SUBJECT")
 private List<Subject> subjects = new ArrayList<>();


    public Student(Long id, AppUser user, Integer grade, List<Lesson> lessons, List<Request> requests, List<Subject> subjects) {
        this.id = id;
        this.user = user;
        this.grade = grade;
        this.lessons = lessons;
        this.requests = requests;
        this.subjects = subjects;
    }

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
