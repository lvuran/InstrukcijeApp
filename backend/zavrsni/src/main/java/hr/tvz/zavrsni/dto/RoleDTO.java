package hr.tvz.zavrsni.dto;

import hr.tvz.zavrsni.model.enumeration.Role;

public class RoleDTO {
    private Role role;

    public RoleDTO() {
    }

    public RoleDTO(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
