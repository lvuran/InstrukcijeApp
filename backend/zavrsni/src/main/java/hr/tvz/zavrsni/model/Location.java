package hr.tvz.zavrsni.model;

import jakarta.persistence.*;


@Entity
@Table(name = "LOCATION")
public class Location {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer x;

    @Column
    private Integer y;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Location(Long id, String name, Integer x, Integer y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Location() {
    }
}
