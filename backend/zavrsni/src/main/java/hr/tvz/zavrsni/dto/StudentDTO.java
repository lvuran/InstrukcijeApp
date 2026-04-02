package hr.tvz.zavrsni.dto;

import hr.tvz.zavrsni.model.Location;
import hr.tvz.zavrsni.model.enumeration.Subject;

import java.util.List;

public class StudentDTO {
    private String address;
    private Location location;
    private String telephone;
    private List<Subject> subjects;
    private Integer grade;

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

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
