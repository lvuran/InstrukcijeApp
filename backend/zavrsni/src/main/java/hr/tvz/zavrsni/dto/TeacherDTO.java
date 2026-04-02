package hr.tvz.zavrsni.dto;

import hr.tvz.zavrsni.model.Location;
import hr.tvz.zavrsni.model.enumeration.Subject;
import hr.tvz.zavrsni.model.enumeration.Type;

import java.util.List;

public class TeacherDTO {
    private String address;
    private Location location;
    private String telephone;
    private String about;
    private List<Subject> subjects;
    private Integer duration;
    private Integer price;
    private Type type;



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public TeacherDTO(String address, Location location, String telephone, String about, List<Subject> subjects, Integer duration, Integer price, Type type) {
        this.address = address;
        this.location = location;
        this.telephone = telephone;
        this.about = about;
        this.subjects = subjects;
        this.duration = duration;
        this.price = price;
        this.type = type;
    }
}
