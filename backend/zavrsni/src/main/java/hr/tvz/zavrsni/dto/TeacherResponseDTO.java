package hr.tvz.zavrsni.dto;

import hr.tvz.zavrsni.model.Location;
import hr.tvz.zavrsni.model.enumeration.Subject;
import hr.tvz.zavrsni.model.enumeration.Type;

import java.util.List;

public class TeacherResponseDTO {

        private Long id;
        private String username;
        private String about;
        private List<Subject> subjects;
        private Integer duration;
        private Integer price;
        private Type type;
        private Float ocjena;
        private Integer ratings;
        private String address;
        private String phone;
        private String mail;

    public TeacherResponseDTO(Long id, String username, String about, List<Subject> subjects, Integer duration, Integer price, Type type, Float ocjena, String address, String phone, String mail, Location location, Integer ratings) {
        this.id = id;
        this.username = username;
        this.about = about;
        this.subjects = subjects;
        this.duration = duration;
        this.price = price;
        this.type = type;
        this.ocjena = ocjena;
        this.address = address;
        this.phone = phone;
        this.mail = mail;
        this.location = location;
        this.ratings = ratings;

    }

    public Integer getRatings() {
        return ratings;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    private Location location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Float getOcjena() {
        return ocjena;
    }

    public void setOcjena(Float ocjena) {
        this.ocjena = ocjena;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
