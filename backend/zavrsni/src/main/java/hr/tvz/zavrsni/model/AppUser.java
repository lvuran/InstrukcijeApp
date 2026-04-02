package hr.tvz.zavrsni.model;
import hr.tvz.zavrsni.model.enumeration.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "APPUSER")
public class AppUser {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column
        private String email;

        @Column
        private String username;

        @NotNull
        @Enumerated(EnumType.STRING)
        private Role role;

        @Column
        private String address;

        @ManyToOne
        @JoinColumn(name = "location_id")
        private Location location;

        @Column
        private String phoneNum;





    public AppUser() {
        }


    public AppUser(String email, String username, Role role) {
            this.email = email;
            this.username = username;
            this.role = role;

        }



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
