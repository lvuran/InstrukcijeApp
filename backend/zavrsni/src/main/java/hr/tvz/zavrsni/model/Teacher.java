package hr.tvz.zavrsni.model;

import hr.tvz.zavrsni.model.enumeration.Subject;
import hr.tvz.zavrsni.model.enumeration.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TEACHER")
public class Teacher{

    @Id
    private Long id;


    @OneToOne
    @MapsId
    private AppUser user;


    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private Type type;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "SUBJECT")
    private List<Subject> subjects = new ArrayList<>();

    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name = "STUDENT_TEACHER",
        joinColumns = @JoinColumn(name = "TEACHER_ID"),
        inverseJoinColumns = @JoinColumn(name = "STUDENT_ID"))
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)

    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)

    private List<Request> requests = new ArrayList<>();

    @Column
    private float rating;

    @Column
    private Integer ratings;

    @Column
    private int duration;

    @Column
    private Integer price;

    @Column
    private String about;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Teacher(Long id, AppUser user, Type type, List<Subject> subjects, List<Student> students, List<Lesson> lessons, List<Request> requests, float ocjena, int duration, Integer price, Integer ratings) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.subjects = subjects;
        this.students = students;
        this.lessons = lessons;
        this.requests = requests;
        this.rating = ocjena;
        this.duration = duration;
        this.price = price;
        this.ratings = ratings;
    }

    public Teacher() {
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
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

    public float getOcjena() {
        return rating;
    }

    public void setOcjena(float ocjena) {
        this.rating = ocjena;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void rate(Integer rating){
        this.ratings++;
        this.rating = (this.rating + rating)/ratings;
    }

    public Integer getRatings() {
        return ratings;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }


}
